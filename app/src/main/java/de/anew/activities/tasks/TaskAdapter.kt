package de.anew.activities.tasks

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.anew.R
import de.anew.activities.tasks.TaskAdapter.Payloads.UPDATE_DUE_DATE_VIEW
import de.anew.activities.tasks.TaskAdapter.TaskViewHolder
import de.anew.models.task.Task
import java.util.concurrent.CopyOnWriteArrayList

class TaskAdapter(
    private val tasksViewModel: TasksViewModel,
    private val taskItemClickListener: TaskItemClickListener,
    private val timeToDueDateFormatter: TimeToDueDateFormatter
) : RecyclerView.Adapter<TaskViewHolder>() {

    private val taskNameViewPosition = 0

    private val dueDateViewPosition = 1

    private lateinit var recyclerView: RecyclerView

    private val tasks = CopyOnWriteArrayList<Task>()

    private val dueDateViewUpdateHandler = Handler(Looper.getMainLooper())

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
        DueDateViewUpdateTrigger(this, dueDateViewUpdateHandler).schedule()
    }

    fun setTasks(tasksToAdd: Collection<Task>) {
        tasks.clear()
        tasks.addAll(tasksToAdd)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val taskView = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_view, parent, false) as LinearLayout
        return TaskViewHolder(taskView, taskItemClickListener)
    }

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
        dueDateView.text = timeToDueDateFormatter.getFormattedDueDate(task)
    }

    fun getPositionOfFirstVisibleTaskView(): Int {
        val firstVisibleChildPosition = recyclerView.getChildAt(0)
        return recyclerView.getChildAdapterPosition(firstVisibleChildPosition)
    }

    fun getNumberOfVisibleTaskViews() = recyclerView.childCount

    fun markTaskAsFulfilled(position: Int) {
        tasksViewModel.markTaskAsFulfilled(tasks[position])
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        removeCallbacksAndMessages()
    }

    fun removeCallbacksAndMessages() {
        dueDateViewUpdateHandler.removeCallbacksAndMessages(null)
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
