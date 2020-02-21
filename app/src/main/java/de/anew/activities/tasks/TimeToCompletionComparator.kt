package de.anew.activities.tasks

import de.anew.models.task.Task
import org.threeten.bp.ZonedDateTime

class TimeToCompletionComparator : Comparator<Task> {

    override fun compare(task1: Task?, task2: Task?): Int {
        if (task1 == null || task2 == null) {
            return 0
        }
        val startingFromNow = ZonedDateTime.now()
        val timeDifference = task1.dueIn(startingFromNow).minus(task2.dueIn(startingFromNow))
        return when {
            timeDifference.isZero -> 0
            timeDifference.isNegative -> -1
            else -> 1
        }
    }

}