package de.quotas.models

import de.quotas.models.time.Daily
import de.quotas.models.time.UndefinedPeriod
import de.quotas.models.time.Weekly
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.ZonedDateTime

class QuotaValidityTest {

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
    fun validQuotaWithNullFulfilmentTime() {
        val quota = Quota(0L, "Quota", Weekly(), START_TIME, null)
        assertThat(quota.isValid()).isTrue()
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

    @Test
    fun nameSetterKeepsInvariants() {
        val quota = Quota(0L, "Quota", Daily(), START_TIME, AFTER_START_TIME)
        quota.name = ""
        assertThat(quota.name).isEqualTo("Quota")
        quota.name = " "
        assertThat(quota.name).isEqualTo("Quota")
        quota.lastFulfillmentTime = START_TIME.minusSeconds(1)
        assertThat(quota.lastFulfillmentTime).isEqualTo(AFTER_START_TIME)
        quota.period = UndefinedPeriod
        assertThat(quota.period).isEqualTo(Daily())
    }

    @Test
    fun nameSetterAcceptsValidValue() {
        val quota = Quota(0L, "Quota", Daily(), START_TIME, AFTER_START_TIME)
        quota.name = quota.name.reversed()
        assertThat(quota.name).isEqualTo("Quota".reversed())
    }

    @Test
    fun lastFulfillmentTimeSetterKeepsInvariants() {
        val quota = Quota(0L, "Quota", Daily(), START_TIME, AFTER_START_TIME)
        quota.lastFulfillmentTime = START_TIME.minusSeconds(1)
        assertThat(quota.lastFulfillmentTime).isEqualTo(AFTER_START_TIME)
    }

    @Test
    fun lastFulfillmentTimeSetterAcceptsValidValue() {
        val quota = Quota(0L, "Quota", Daily(), START_TIME, AFTER_START_TIME)
        quota.lastFulfillmentTime = START_TIME
        assertThat(quota.lastFulfillmentTime).isEqualTo(START_TIME)
        quota.lastFulfillmentTime = START_TIME.plusDays(1)
        assertThat(quota.lastFulfillmentTime).isEqualTo(START_TIME.plusDays(1))
    }

    @Test
    fun periodSetterKeepsInvariants() {
        val quota = Quota(0L, "Quota", Daily(), START_TIME, AFTER_START_TIME)
        quota.period = UndefinedPeriod
        assertThat(quota.period).isEqualTo(Daily())
    }

    @Test
    fun periodSetterAcceptsValidValue() {
        val quota = Quota(0L, "Quota", Daily(), START_TIME, AFTER_START_TIME)
        quota.period = Weekly()
        assertThat(quota.period).isEqualTo(Weekly())
    }

    companion object {

        val START_TIME: ZonedDateTime = ZonedDateTime.parse("2019-11-16T10:34:24.621+01:00[Europe/Berlin]")

        val AFTER_START_TIME: ZonedDateTime = START_TIME.plusMinutes(1)

    }

}