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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.util.*

class TaskAdapter(
    private val tasksViewModel: TasksViewModel,
    private val taskItemClickListener: TaskItemClickListener,
    private val timeToDueDateFormatter: TimeToDueDateFormatter
) : RecyclerView.Adapter<TaskViewHolder>() {

    private val taskNameViewPosition = 0

    private val dueDateViewPosition = 1

    private lateinit var recyclerView: RecyclerView

    private val tasks = mutableListOf<Task>()

    private val timeToDueDateCache = mutableMapOf<Task, String>()

    private val pendingTaskUpdates = ArrayDeque<List<Task>>()

    private val scope = tasksViewModel.viewModelScope

    private val updateMutex = Mutex()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
        PeriodicViewUpdater(this, tasks, timeToDueDateCache, timeToDueDateFormatter, updateMutex).schedule(scope)
    }

    fun setTasks(newTasks: List<Task>) {
        val copyOfNewTasks = newTasks.toList()
        pendingTaskUpdates.add(copyOfNewTasks)
        if (pendingTaskUpdates.size <= 1) {
            updateViewAndTasks(copyOfNewTasks)
        }

    }

    private fun updateViewAndTasks(newTasks: List<Task>) {
        scope.launch(Dispatchers.Main) {
            val diffResult = withContext(Dispatchers.Default) {
                DiffUtil.calculateDiff(TasksDiffCallback(tasks, newTasks))
            }
            updateMutex.withLock {
                dispatchUpdatesToView(diffResult)
                updateTasks(newTasks)
            }
            pendingTaskUpdates.remove()
            if (pendingTaskUpdates.size > 0) {
                updateViewAndTasks(pendingTaskUpdates.peek())
            }
        }
    }

    private fun dispatchUpdatesToView(diffResult: DiffResult) = diffResult.dispatchUpdatesTo(this)

    private fun updateTasks(newTasks: List<Task>) {
        timeToDueDateCache.keys.retainAll(newTasks)
        tasks.clear()
        tasks.addAll(newTasks)
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
        setTaskName(holder, task)
        setDueDate(holder, task)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.contains(UPDATE_DUE_DATE_VIEW)) {
            setDueDate(holder, tasks[position])
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    private fun setTaskName(holder: TaskViewHolder, task: Task) {
        val taskView = holder.taskView
        val taskNameView = taskView.getChildAt(taskNameViewPosition) as TextView
        taskNameView.text = task.name
    }

    private fun setDueDate(holder: TaskViewHolder, task: Task) {
        val taskView = holder.taskView
        val dueDateView = taskView.getChildAt(dueDateViewPosition) as TextView
        dueDateView.text = timeToDueDateCache.computeIfAbsent(task, timeToDueDateFormatter::formatDueDate)
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

    enum class Payloads {
        UPDATE_DUE_DATE_VIEW
    }

}
