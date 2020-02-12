package de.anew.models

import de.anew.models.time.Daily
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.ZonedDateTime

class TaskFulfillmentTest {

    @Test
    fun taskNeverFulfilled() {
        val task = Task(0L, "Task", Daily(), START_TIME, null)
        assertThat(task.isFulfilled()).isFalse()
    }

    @Test
    fun taskFulfilled() {
        val task = Task(0L, "Task", Daily(), START_TIME, ZonedDateTime.now())
        assertThat(task.isFulfilled()).isTrue()
    }

    @Test
    fun taskNotFulfilled() {
        val task = Task(0L, "Task", Daily(), START_TIME, ZonedDateTime.now().minusDays(2))
        assertThat(task.isFulfilled()).isFalse()
    }

    companion object {

        val START_TIME: ZonedDateTime = ZonedDateTime.parse("2019-11-16T10:34:24.621+01:00[Europe/Berlin]")

    }

}