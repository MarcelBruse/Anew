package de.quotas.models

import de.quotas.models.time.Daily
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.*

class DailyQuotaOverdueTest {

    @Test
    fun twentySixHoursSinceDueDateOfYetUnfulfilledDailyQuota() {
        val fixedTime = Instant.parse("2020-01-29T22:00:00.000Z")
        val fixedClock = Clock.fixed(fixedTime, ZoneId.of("Europe/London"))
        val now = ZonedDateTime.now(fixedClock)
        val startTime = now.minusDays(1)
        val quota = Quota(0, "", Daily(fixedClock), startTime, null)
        assertThat(quota.dueIn().abs()).isEqualTo(Duration.ofHours(22))
        assertThat(quota.dueIn().isNegative).isTrue()
    }

    @Test
    fun fortyFourHoursSinceDueDateOfFulfilledDailyQuota() {
        val fixedTime = Instant.parse("2020-01-29T20:00:00.000Z")
        val fixedClock = Clock.fixed(fixedTime, ZoneId.of("Europe/London"))
        val now = ZonedDateTime.now(fixedClock)
        val startTime = now.minusDays(10)
        val lastFulfillmentTime = now.minusDays(3)
        val quota = Quota(0, "", Daily(fixedClock), startTime, lastFulfillmentTime)
        assertThat(quota.dueIn().abs()).isEqualTo(Duration.ofHours(44))
        assertThat(quota.dueIn().isNegative).isTrue()
    }

}