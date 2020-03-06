package de.anew.activities.tasks

import android.content.Context
import de.anew.R
import de.anew.models.task.Task
import de.anew.models.time.TimePeriodEnum

class TimeToDueDateFormatter(context: Context) {

    private val periodNames: HashMap<TimePeriodEnum, String> = hashMapOf(
        TimePeriodEnum.DAILY to context.getString(R.string.daily),
        TimePeriodEnum.WEEKLY to context.getString(R.string.weekly),
        TimePeriodEnum.UNDEFINED_PERIOD to "Unknown period"
    )

    private val dueInLabel = context.getString(R.string.due_in)

    private val overdueSinceLabel = context.getString(R.string.overdue_since)

    private val durationFormatter = DurationFormatter(context)

    fun getFormattedDueDate(task: Task): String {
        val timePeriodEnum = TimePeriodEnum.getByTimePeriod(task.period)
        val periodName = periodNames[timePeriodEnum]
        val dueIn = task.dueIn()
        val dueOrOverdue = if (dueIn.isNegative) overdueSinceLabel else dueInLabel
        val formattedDuration = durationFormatter.format(dueIn)
        return "%s · %s %s".format(periodName, dueOrOverdue, formattedDuration)
    }

}