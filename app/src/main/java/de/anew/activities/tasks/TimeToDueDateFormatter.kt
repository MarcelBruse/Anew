package de.anew.activities.tasks

import android.content.Context
import de.anew.R
import de.anew.models.task.Task

class TimeToDueDateFormatter(private val context: Context) {

    fun getFormattedDueDate(task: Task): String {
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

}