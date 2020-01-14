package de.quotas.models

import de.quotas.models.time.Daily
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.ZonedDateTime

class QuotaFulfilmentTest {

    @Test
    fun quotaNeverFulfilled() {
        val quota = Quota(0L, "Quota", Daily(), START_TIME, null)
        assertThat(quota.isFulfilled()).isFalse()
    }

    @Test
    fun quotaFulfilled() {
        val quota = Quota(0L, "Quota", Daily(), START_TIME, ZonedDateTime.now())
        assertThat(quota.isFulfilled()).isTrue()
    }

    @Test
    fun quotaNotFulfilled() {
        val quota = Quota(0L, "Quota", Daily(), START_TIME, ZonedDateTime.now().minusDays(2))
        assertThat(quota.isFulfilled()).isFalse()
    }

    companion object {

        val START_TIME: ZonedDateTime = ZonedDateTime.parse("2019-11-16T10:34:24.621+01:00[Europe/Berlin]")

    }

}