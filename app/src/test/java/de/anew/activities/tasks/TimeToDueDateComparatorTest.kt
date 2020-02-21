package de.anew.activities.tasks

import de.anew.models.task.Task
import de.anew.models.time.Daily
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.ZonedDateTime

class TimeToDueDateComparatorTest {

    @Test
    fun firstTaskIsDueBeforeSecondTask() {
        val now = ZonedDateTime.now()
        val task1 = Task(0L, "Task1", Daily(), now.minusDays(2), null)
        val task2 = Task(1L, "Task2", Daily(), now, null)
        assertThat(TimeToDueDateComparator().compare(task1, task2)).isNegative()
    }

    @Test
    fun firstTaskIsDueAfterSecondTask() {
        val now = ZonedDateTime.now()
        val task1 = Task(0L, "Task1", Daily(), now, null)
        val task2 = Task(1L, "Task2", Daily(), now.minusDays(2), null)
        assertThat(TimeToDueDateComparator().compare(task1, task2)).isPositive()
    }

    @Test
    fun firstAndSecondTaskAreDueAtTheSameTime() {
        val now = ZonedDateTime.now()
        val task1 = Task(0L, "Task1", Daily(), now, null)
        val task2 = Task(1L, "Task2", Daily(), now, null)
        assertThat(TimeToDueDateComparator().compare(task1, task2)).isEqualTo(0)
    }

    @Test
    fun tasksAreNull() {
        assertThat(TimeToDueDateComparator().compare(null, null)).isEqualTo(0)
    }

}