package de.anew.activities.tasks

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.anew.R
import de.anew.activities.tasks.TaskAdapter.TaskViewHolder
import de.anew.models.task.Task
class TaskAdapter(
    private val tasksViewModel: TasksViewModel,
    private val taskItemClickListener: TaskItemClickListener
) : RecyclerView.Adapter<TaskViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val taskView = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_view, parent, false) as LinearLayout
        return TaskViewHolder(taskView, taskItemClickListener)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        context = recyclerView.context
    }

    override fun getItemCount() = tasksViewModel.tasks.value?.size ?: 0

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val taskView = holder.taskView
        val taskNameView = taskView.getChildAt(0) as TextView
        val dueDateView = taskView.getChildAt(1) as TextView
        tasksViewModel.tasks.value?.let {
            val task = it[position]
            taskNameView.text = task.name
            dueDateView.text = getFormattedDueDate(task)
        }
    }

    private fun getFormattedDueDate(task: Task): String {
        val dueIn = task.dueIn()
        val periodName = getLocalizedPeriodName(task)
        val dueOrOverdueId = if (dueIn.isNegative) R.string.overdue_since else R.string.due_in
        val dueOrOverdue = context.getString(dueOrOverdueId)
        val formattedDuration = DurationFormatter(context).format(dueIn)
        return "%s · %s %s".format(periodName, dueOrOverdue, formattedDuration)
    }

    private fun getLocalizedPeriodName(task: Task): String {
        return when (task.period::class.java.simpleName) {
            "Daily" -> context.getString(R.string.daily)
            "Weekly" -> context.getString(R.string.weekly)
            else -> "Undefined period"
        }
    }

    fun markTaskAsFulfullied(position: Int) {
        tasksViewModel.tasks.value?.get(position)?.let {
            tasksViewModel.markTaskAsFulfilled(it)
        }
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

}

