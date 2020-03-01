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

class TaskAdapter(
    private val tasksViewModel: TasksViewModel,
    private val taskItemClickListener: TaskItemClickListener,
    private val timeToDueDateFormatter: TimeToDueDateFormatter
) : RecyclerView.Adapter<TaskViewHolder>() {

    private val taskNameViewPosition = 0

    private val dueDateViewPosition = 1

    private val dueDateViewUpdateHandler = Handler(Looper.getMainLooper())

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        DueDateViewUpdateTrigger(this, dueDateViewUpdateHandler).schedule()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val taskView = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_view, parent, false) as LinearLayout
        return TaskViewHolder(taskView, taskItemClickListener)
    }

    override fun getItemCount() = tasksViewModel.tasks.value?.size ?: 0

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        tasksViewModel.tasks.value?.let {
            val task = it[position]
            bindTaskNameView(holder, task)
            bindDueDateView(holder, task)
        }
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.contains(UPDATE_DUE_DATE_VIEW)) {
            tasksViewModel.tasks.value?.let {
                val task = it[position]
                bindDueDateView(holder, task)
            }
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    private fun bindTaskNameView(holder: TaskViewHolder, task: Task) {
        val taskView = holder.taskView
        val taskNameView = taskView.getChildAt(taskNameViewPosition) as TextView
        taskNameView.text = task.name
    }

    private fun bindDueDateView(holder: TaskViewHolder, task: Task) {
        val taskView = holder.taskView
        val dueDateView = taskView.getChildAt(dueDateViewPosition) as TextView
        dueDateView.text = timeToDueDateFormatter.getFormattedDueDate(task)
    }

    fun markTaskAsFulfilled(position: Int) {
        tasksViewModel.tasks.value?.get(position)?.let {
            tasksViewModel.markTaskAsFulfilled(it)
        }
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
