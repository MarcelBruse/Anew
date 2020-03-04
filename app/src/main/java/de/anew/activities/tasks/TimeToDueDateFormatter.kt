package de.anew.activities.tasks

import android.content.Context
import de.anew.R
import de.anew.models.task.Task

class TimeToDueDateFormatter(context: Context) {

    private val periodNames: HashMap<String, String> = hashMapOf(
        "Daily" to context.getString(R.string.daily),
        "Weekly" to context.getString(R.string.weekly)
    )

    private val dueInLabel = context.getString(R.string.due_in)

    private val overdueSinceLabel = context.getString(R.string.overdue_since)

    private val durationFormatter = DurationFormatter(context)

    fun getFormattedDueDate(task: Task): String {
        val dueIn = task.dueIn()
        val periodName = periodNames.getOrDefault(task.period::class.java.simpleName, "Unknown period")
        val dueOrOverdue = if (dueIn.isNegative) overdueSinceLabel else dueInLabel
        val formattedDuration = durationFormatter.format(dueIn)
        return "%s · %s %s".format(periodName, dueOrOverdue, formattedDuration)
    }

}