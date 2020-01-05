package de.quotas.models

import de.quotas.models.time.Daily
import de.quotas.models.time.UndefinedPeriod
import de.quotas.models.time.Weekly
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.ZonedDateTime

class QuotaValidityTest {

    @Test
    fun invalidQuotaWithNullName() {
        val quota = Quota(0L, null, Daily(), START_TIME, AFTER_START_TIME)
        assertThat(quota.isValid()).isFalse()
    }

    @Test
    fun invalidQuotaWithEmptyName() {
        val quota = Quota(0L, "", Daily(), START_TIME, AFTER_START_TIME)
        assertThat(quota.isValid()).isFalse()
    }

    @Test
    fun invalidQuotaWithBlankName() {
        val quota = Quota(0L, " ", Daily(), START_TIME, AFTER_START_TIME)
        assertThat(quota.isValid()).isFalse()
    }

    @Test
    fun invalidQuotaWithNullPeriod() {
        val quota = Quota(0L, "Quota", null, START_TIME, AFTER_START_TIME)
        assertThat(quota.isValid()).isFalse()
    }

    @Test
    fun invalidQuotaWithNullStartTime() {
        val quota = Quota(0L, "Quota", Weekly(), null, AFTER_START_TIME)
        assertThat(quota.isValid()).isFalse()
    }

    @Test
    fun invalidQuotaWithNullFulfilmentTime() {
        val quota = Quota(0L, "Quota", Weekly(), START_TIME, null)
        assertThat(quota.isValid()).isFalse()
    }

    @Test
    fun invalidQuotaWithUndefinedPeriod() {
        val quota = Quota(0L, "Quota", UndefinedPeriod, START_TIME, AFTER_START_TIME)
        assertThat(quota.isValid()).isFalse()
    }

    @Test
    fun invalidQuotaWithStartAfterFulfilmentTime() {
        val quota = Quota(0L, "Quota", Daily(), START_TIME.plusSeconds(1), START_TIME)
        assertThat(quota.isValid()).isFalse()
    }

    @Test
    fun validQuotaWithSameStartAndFulfilmentTime() {
        val quota = Quota(0L, "Quota", Daily(), START_TIME, START_TIME)
        assertThat(quota.isValid()).isTrue()
    }

    @Test
    fun validQuotaWithStartTimeBeforeFulfilmentTime() {
        val quota = Quota(0L, "Quota", Daily(), START_TIME, AFTER_START_TIME)
        assertThat(quota.isValid()).isTrue()
    }

    companion object {

        val START_TIME: ZonedDateTime = ZonedDateTime.parse("2019-11-16T10:34:24.621+01:00[Europe/Berlin]")

        val AFTER_START_TIME: ZonedDateTime = START_TIME.plusMinutes(1)

    }

}