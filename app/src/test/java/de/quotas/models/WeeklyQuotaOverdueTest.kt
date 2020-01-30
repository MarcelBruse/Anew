package de.quotas.models

import de.quotas.models.time.Weekly
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.*

class WeeklyQuotaOverdueTest {

    @Test
    fun twoDaysSinceDueDateOfYetUnfulfilledDailyQuota() {
        val fixedTime = Instant.parse("2020-01-29T00:00:00.000Z")
        val fixedClock = Clock.fixed(fixedTime, ZoneId.of("Europe/London"))
        val now = ZonedDateTime.now(fixedClock)
        val startTime = now.minusWeeks(1)
        val quota = Quota(0, "", Weekly(fixedClock), startTime, null)
        assertThat(quota.dueIn().abs()).isEqualTo(Duration.ofDays(2))
        assertThat(quota.dueIn().isNegative).isTrue()
    }

    @Test
    fun twoDaysSinceDueDateOfFulfilledDailyQuota() {
        val fixedTime = Instant.parse("2020-01-29T00:00:00.000Z")
        val fixedClock = Clock.fixed(fixedTime, ZoneId.of("Europe/London"))
        val now = ZonedDateTime.now(fixedClock)
        val startTime = now.minusWeeks(20)
        val lastFulfillmentTime = now.minusWeeks(2)
        val quota = Quota(0, "", Weekly(fixedClock), startTime, lastFulfillmentTime)
        assertThat(quota.dueIn().abs()).isEqualTo(Duration.ofDays(2))
        assertThat(quota.dueIn().isNegative).isTrue()
    }

}