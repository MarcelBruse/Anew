package de.anew.models.task

import de.anew.models.task.Task
import de.anew.models.time.Weekly
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.*

class WeeklyTaskOverdueTest {

    @Test
    fun twoDaysSinceDueDateOfYetUnfulfilledDailyTask() {
        val fixedTime = Instant.parse("2020-01-29T00:00:00.000Z")
        val fixedClock = Clock.fixed(fixedTime, ZoneId.of("Europe/London"))
        val now = ZonedDateTime.now(fixedClock)
        val startTime = now.minusWeeks(1)
        val task =
            Task(0, "", Weekly(fixedClock), startTime, null)
        assertThat(task.dueIn().abs()).isEqualTo(Duration.ofDays(2))
        assertThat(task.dueIn().isNegative).isTrue()
    }

    @Test
    fun twoDaysSinceDueDateOfFulfilledDailyTask() {
        val fixedTime = Instant.parse("2020-01-29T00:00:00.000Z")
        val fixedClock = Clock.fixed(fixedTime, ZoneId.of("Europe/London"))
        val now = ZonedDateTime.now(fixedClock)
        val startTime = now.minusWeeks(20)
        val lastFulfillmentTime = now.minusWeeks(2)
        val task = Task(
            0,
            "",
            Weekly(fixedClock),
            startTime,
            lastFulfillmentTime
        )
        assertThat(task.dueIn().abs()).isEqualTo(Duration.ofDays(2))
        assertThat(task.dueIn().isNegative).isTrue()
    }

}