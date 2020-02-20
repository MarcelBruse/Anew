package de.anew.models

import de.anew.models.task.Task
import de.anew.models.time.Daily
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.*

class DailyTaskOverdueTest {

    @Test
    fun twentySixHoursSinceDueDateOfYetUnfulfilledDailyTask() {
        val fixedTime = Instant.parse("2020-01-29T22:00:00.000Z")
        val fixedClock = Clock.fixed(fixedTime, ZoneId.of("Europe/London"))
        val now = ZonedDateTime.now(fixedClock)
        val startTime = now.minusDays(1)
        val task =
            Task(0, "", Daily(fixedClock), startTime, null)
        assertThat(task.dueIn().abs()).isEqualTo(Duration.ofHours(22))
        assertThat(task.dueIn().isNegative).isTrue()
    }

    @Test
    fun fortyFourHoursSinceDueDateOfFulfilledDailyTask() {
        val fixedTime = Instant.parse("2020-01-29T20:00:00.000Z")
        val fixedClock = Clock.fixed(fixedTime, ZoneId.of("Europe/London"))
        val now = ZonedDateTime.now(fixedClock)
        val startTime = now.minusDays(10)
        val lastFulfillmentTime = now.minusDays(3)
        val task = Task(
            0,
            "",
            Daily(fixedClock),
            startTime,
            lastFulfillmentTime
        )
        assertThat(task.dueIn().abs()).isEqualTo(Duration.ofHours(44))
        assertThat(task.dueIn().isNegative).isTrue()
    }

}