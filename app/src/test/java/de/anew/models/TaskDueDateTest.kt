package de.anew.models

import de.anew.models.task.Task
import de.anew.models.time.Daily
import de.anew.models.time.Weekly
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.ZonedDateTime

class TaskDueDateTest {

    @Test
    fun dueDateOfYetUnfulfilledDailyTask() {
        val startTime = ZonedDateTime.parse("2020-01-27T17:30:00+01:00[Europe/Berlin]")
        val expectedDueDate = ZonedDateTime.parse("2020-01-28T00:00:00+01:00[Europe/Berlin]")
        val task = Task(0, "", Daily(), startTime, null)
        assertThat(task.dueDate()).isEqualTo(expectedDueDate)
    }

    @Test
    fun dueDateOfYetUnfulfilledWeeklyTask() {
        val startTime = ZonedDateTime.parse("2020-01-27T17:30:00+01:00[Europe/Berlin]")
        val expectedDueDate = ZonedDateTime.parse("2020-02-03T00:00:00+01:00[Europe/Berlin]")
        val task = Task(0, "", Weekly(), startTime, null)
        assertThat(task.dueDate()).isEqualTo(expectedDueDate)
    }

    @Test
    fun dueDateOfFulfilledDailyTask() {
        val startTime = ZonedDateTime.parse("2020-01-01T00:00:00+01:00[Europe/Berlin]")
        val lastFulfillmentTime = ZonedDateTime.parse("2020-01-22T17:30:00+01:00[Europe/Berlin]")
        val expectedDueDate = ZonedDateTime.parse("2020-01-24T00:00:00+01:00[Europe/Berlin]")
        val task = Task(
            0,
            "",
            Daily(),
            startTime,
            lastFulfillmentTime
        )
        assertThat(task.dueDate()).isEqualTo(expectedDueDate)
    }


    @Test
    fun dueDateOfFulfilledWeeklyTask() {
        val startTime = ZonedDateTime.parse("2020-01-01T00:00:00+01:00[Europe/Berlin]")
        val lastFulfillmentTime = ZonedDateTime.parse("2020-01-22T17:30:00+01:00[Europe/Berlin]")
        val expectedDueDate = ZonedDateTime.parse("2020-02-03T00:00:00+01:00[Europe/Berlin]")
        val task = Task(
            0,
            "",
            Weekly(),
            startTime,
            lastFulfillmentTime
        )
        assertThat(task.dueDate()).isEqualTo(expectedDueDate)
    }

}