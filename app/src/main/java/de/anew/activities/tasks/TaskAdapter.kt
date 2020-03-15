package de.anew.activities.tasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult
import androidx.recyclerview.widget.RecyclerView
import de.anew.R
import de.anew.activities.tasks.TaskAdapter.Payloads.UPDATE_DUE_DATE_VIEW
import de.anew.activities.tasks.TaskAdapter.TaskViewHolder
import de.anew.models.task.Task
import de.anew.models.time.TimePeriod
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import org.threeten.bp.Duration
import java.util.*

class TaskAdapter(
    private val tasksViewModel: TasksViewModel,
    private val taskItemClickListener: TaskItemClickListener
) : RecyclerView.Adapter<TaskViewHolder>() {

    private val taskNameViewPosition = 0

    private val dueDateViewPosition = 1

    private lateinit var recyclerView: RecyclerView

    private lateinit var periodicUpdater: PeriodicViewUpdater

    private val tasks = mutableListOf<Task>()

    private val taskPropertyCache = mutableMapOf<Task, TaskViewProperties>()

    private val pendingTaskUpdates = ArrayDeque<List<Task>>()

    private lateinit var timeToDueDateFormatter: TimeToDueDateFormatter

    private lateinit var taskColorizer: TaskColorizer

    private val scope = tasksViewModel.viewModelScope

    private val updateMutex = Mutex()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
        timeToDueDateFormatter = TimeToDueDateFormatter(recyclerView.context)
        taskColorizer = TaskColorizer(recyclerView.context)
        periodicUpdater = PeriodicViewUpdater(this, updateMutex)
        periodicUpdater.schedule(scope)
    }

    fun setTasks(newTasks: List<Task>) {
        val copyOfNewTasks = newTasks.toList()
        pendingTaskUpdates.add(copyOfNewTasks)
        if (pendingTaskUpdates.size <= 1) {
            updateViewAndTasks(copyOfNewTasks)
        }
    }

    fun getTasks() = tasks.toList()

    private fun updateViewAndTasks(newTasks: List<Task>) {
        scope.launch(Dispatchers.Main) {
            val diffResult = withContext(Dispatchers.Default) {
                DiffUtil.calculateDiff(TasksDiffCallback(tasks, newTasks))
            }
            updateMutex.withLock {
                dispatchUpdatesToView(diffResult)
                updateTasks(newTasks)
            }
            updateViewImmediately()
            pendingTaskUpdates.remove()
            if (pendingTaskUpdates.size > 0) {
                updateViewAndTasks(pendingTaskUpdates.peek())
            }
        }
    }

    private fun dispatchUpdatesToView(diffResult: DiffResult) {
        diffResult.dispatchUpdatesTo(this)
    }

    private fun updateTasks(newTasks: List<Task>) {
        taskPropertyCache.keys.retainAll(newTasks)
        tasks.clear()
        tasks.addAll(newTasks)
    }

    fun updateTaskPropertyCache(newTaskPropertyCache: Map<Task, TaskViewProperties>) {
        taskPropertyCache.putAll(newTaskPropertyCache)
    }

    fun updateViewImmediately() {
        periodicUpdater.updateImmediately(scope)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val taskView = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_view, parent, false) as LinearLayout
        return TaskViewHolder(taskView, taskItemClickListener)
    }

    override fun getItemId(position: Int) = tasks[position].id

    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        val cachedTaskProperties = getCachedTaskProperties(task)
        setTaskName(holder, task)
        setDueDate(holder, cachedTaskProperties)
        setColors(holder, cachedTaskProperties)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.contains(UPDATE_DUE_DATE_VIEW)) {
            val cachedTaskProperties = getCachedTaskProperties(tasks[position])
            setDueDate(holder, cachedTaskProperties)
            setColors(holder, cachedTaskProperties)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    private fun getCachedTaskProperties(task: Task) = taskPropertyCache.computeIfAbsent(task, this::createCachedTaskProperties)

    private fun createCachedTaskProperties(task: Task): TaskViewProperties {
        val isFulfilled = task.isFulfilled()
        val dueIn = task.dueIn()
        val timeToDueDate = formatDueDate(task.period, dueIn)
        val fontColor = getTaskFontColor(isFulfilled, dueIn)
        val backgroundColor = getTaskBackgroundColor(isFulfilled, dueIn)
        return TaskViewProperties(timeToDueDate, fontColor, backgroundColor)
    }

    fun formatDueDate(period: TimePeriod, dueIn: Duration) = timeToDueDateFormatter.formatDueDate(period, dueIn)

    fun getTaskFontColor(taskIsFulfilled: Boolean, dueIn: Duration) = taskColorizer.getFontColor(taskIsFulfilled, dueIn)

    fun getTaskBackgroundColor(taskIsFulfilled: Boolean, dueIn: Duration) = taskColorizer.getBackgroundColor(taskIsFulfilled, dueIn)

    private fun setTaskName(holder: TaskViewHolder, task: Task) {
        val taskView = holder.taskView
        val taskNameView = taskView.getChildAt(taskNameViewPosition) as TextView
        taskNameView.text = task.name
    }

    private fun setDueDate(holder: TaskViewHolder, taskViewProperties: TaskViewProperties) {
        val taskView = holder.taskView
        val dueDateView = taskView.getChildAt(dueDateViewPosition) as TextView
        dueDateView.text = taskViewProperties.timeToDueDate
    }

    private fun setColors(holder: TaskViewHolder, taskViewProperties: TaskViewProperties) {
        val taskView = holder.taskView
        taskView.setBackgroundColor(taskViewProperties.backgroundColor)
        val textView = taskView.getChildAt(taskNameViewPosition) as TextView
        val dueDateView = taskView.getChildAt(dueDateViewPosition) as TextView
        textView.setTextColor(taskViewProperties.fontColor)
        dueDateView.setTextColor(taskViewProperties.fontColor)
    }

    fun getPositionOfFirstVisibleTaskView(): Int {
        val firstVisibleChildPosition = recyclerView.getChildAt(0)
        return recyclerView.getChildAdapterPosition(firstVisibleChildPosition)
    }

    fun getNumberOfVisibleTaskViews() = recyclerView.childCount

    fun markTaskAsFulfilled(position: Int) {
        tasksViewModel.markTaskAsFulfilled(tasks[position])
    }

    class TaskViewHolder(
        val taskView: LinearLayout,
        private val taskItemClickListener: TaskItemClickListener
    ) : RecyclerView.ViewHolder(taskView), View.OnClickListener {

        init {
            taskView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            taskItemClickListener.taskItemClickedAt(this.layoutPosition)
        }

    }

    data class TaskViewProperties(val timeToDueDate: String, val fontColor: Int, val backgroundColor: Int)

    enum class Payloads {
        UPDATE_DUE_DATE_VIEW
    }

}
