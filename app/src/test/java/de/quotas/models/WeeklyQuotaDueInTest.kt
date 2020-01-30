package de.quotas.models

import de.quotas.models.time.Weekly
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.*

class WeeklyQuotaDueInTest {

    @Test
    fun seventyFourHoursUntilDueDateOfYetUnfulfilledWeeklyQuota() {
        val fixedTime = Instant.parse("2020-01-30T22:00:00.000Z")
        val fixedClock = Clock.fixed(fixedTime, ZoneId.of("Europe/London"))
        val now = ZonedDateTime.now(fixedClock)
        val startTime = now.minusDays(2)
        val quota = Quota(0, "", Weekly(fixedClock), startTime, null)
        assertThat(quota.dueIn()).isEqualTo(Duration.ofHours(74))
    }

    @Test
    fun timeUntilDueDateOfWeeklyQuotaFulfilledLastWeek() {
        val fixedTime = Instant.parse("2020-01-30T22:00:00.000Z")
        val fixedClock = Clock.fixed(fixedTime, ZoneId.of("Europe/London"))
        val now = ZonedDateTime.now(fixedClock)
        val startTime = now.minusDays(100)
        val lastFulfillmentTime = now.minusWeeks(1)
        val quota = Quota(0, "", Weekly(fixedClock), startTime, lastFulfillmentTime)
        assertThat(quota.dueIn()).isEqualTo(Duration.ofHours(74))
    }

    @Test
    fun timeUntilDueDateOfWeeklyQuotaFulfilledThisWeek() {
        val fixedTime = Instant.parse("2020-01-30T22:00:00.000Z")
        val fixedClock = Clock.fixed(fixedTime, ZoneId.of("Europe/London"))
        val now = ZonedDateTime.now(fixedClock)
        val startTime = now.minusDays(100)
        val lastFulfillmentTime = now.minusDays(1)
        val quota = Quota(0, "", Weekly(fixedClock), startTime, lastFulfillmentTime)
        assertThat(quota.dueIn()).isEqualTo(Duration.ofDays(7).plusHours(74))
    }

}