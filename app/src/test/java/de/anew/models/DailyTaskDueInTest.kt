package de.anew.models

import de.anew.models.time.Daily
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.*

class DailyTaskDueInTest {

    @Test
    fun twoHoursUntilDueDateOfYetUnfulfilledDailyTask() {
        val fixedTime = Instant.parse("2020-01-29T22:00:00.000Z")
        val fixedClock = Clock.fixed(fixedTime, ZoneId.of("Europe/London"))
        val now = ZonedDateTime.now(fixedClock)
        val startTime = now.minusHours(1)
        val task = Task(0, "", Daily(fixedClock), startTime, null)
        assertThat(task.dueIn()).isEqualTo(Duration.ofHours(2))
    }

    @Test
    fun timeUntilDueDateOfDailyTaskFulfilledYesterday() {
        val fixedTime = Instant.parse("2020-01-29T20:00:00.000Z")
        val fixedClock = Clock.fixed(fixedTime, ZoneId.of("Europe/London"))
        val now = ZonedDateTime.now(fixedClock)
        val startTime = now.minusDays(10)
        val lastFulfillmentTime = now.minusDays(1)
        val task = Task(0, "", Daily(fixedClock), startTime, lastFulfillmentTime)
        assertThat(task.dueIn()).isEqualTo(Duration.ofHours(4))
    }

    @Test
    fun timeUntilDueDateOfDailyTaskFulfilledToday() {
        val fixedTime = Instant.parse("2020-01-29T20:00:00.000Z")
        val fixedClock = Clock.fixed(fixedTime, ZoneId.of("Europe/London"))
        val now = ZonedDateTime.now(fixedClock)
        val startTime = now.minusDays(10)
        val lastFulfillmentTime = now.minusHours(1)
        val task = Task(0, "", Daily(fixedClock), startTime, lastFulfillmentTime)
        assertThat(task.dueIn()).isEqualTo(Duration.ofHours(28))
    }

    @Test
    fun dayTimeSavingTwentyThreeHourDay() {
        val fixedTime = ZonedDateTime.parse("2020-03-29T00:00+01:00[Europe/Berlin]")
        val fixedClock = Clock.fixed(fixedTime.toInstant(), ZoneId.of("Europe/Berlin"))
        val startTime = ZonedDateTime.now(fixedClock).minusDays(5)
        val lastFulfillmentTime = ZonedDateTime.now(fixedClock).minusHours(10)
        val task = Task(0, "", Daily(fixedClock), startTime, lastFulfillmentTime)
        assertThat(task.dueIn()).isEqualTo(Duration.ofHours(23))
    }


    @Test
    fun dayTimeSavingTwentyFiveHourDay() {
        val fixedTime = ZonedDateTime.parse("2020-10-25T00:00:00+02:00[Europe/Berlin]")
        val fixedClock = Clock.fixed(fixedTime.toInstant(), ZoneId.of("Europe/Berlin"))
        val startTime = ZonedDateTime.now(fixedClock).minusDays(5)
        val lastFulfillmentTime = ZonedDateTime.now(fixedClock).minusHours(10)
        val task = Task(0, "", Daily(fixedClock), startTime, lastFulfillmentTime)
        assertThat(task.dueIn()).isEqualTo(Duration.ofHours(25))
    }

}