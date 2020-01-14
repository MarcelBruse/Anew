package de.quotas.models

import de.quotas.models.QuotaValidator.Error.*
import de.quotas.models.time.Daily
import de.quotas.models.time.UndefinedPeriod
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.ZonedDateTime

class QuotaValidatorTest {

    @Test
    fun validQuotaNameWithMaximumLength() {
        val errors = QuotaValidator.validateQuotaName("1234567890123456789012345678901234567890")
        assertThat(errors).isEmpty()
    }

    @Test
    fun invalidQuotaNameExceedsMaximumLength() {
        val errors = QuotaValidator.validateQuotaName("12345678901234567890123456789012345678901")
        assertThat(errors).hasSize(1)
        assertThat(errors).containsOnly(QUOTA_NAME_LENGTH_EXCEEDS_LIMIT)
    }

    @Test
    fun invalidEmptyQuotaName() {
        val errors = QuotaValidator.validateQuotaName("")
        assertThat(errors).hasSize(1)
        assertThat(errors).containsOnly(QUOTA_NAME_IS_BLANK)
    }

    @Test
    fun invalidBlankQuotaName() {
        val errors = QuotaValidator.validateQuotaName(" ")
        assertThat(errors).containsOnly(QUOTA_NAME_IS_BLANK)
    }

    @Test
    fun validPeriod() {
        val errors = QuotaValidator.validatePeriod(Daily())
        assertThat(errors).isEmpty()
    }

    @Test
    fun invalidUndefinedPeriod() {
        val errors = QuotaValidator.validatePeriod(UndefinedPeriod)
        assertThat(errors).containsOnly(UNDEFINED_PERIOD)
    }

    @Test
    fun validQuota() {
        val quota = Quota(0L, "Some quota", Daily(), ZonedDateTime.now(), ZonedDateTime.now())
        val errors = QuotaValidator.validate(quota)
        assertThat(errors).isEmpty()
    }

    @Test
    fun invalidQuota() {
        val quota = Quota(0L, "", UndefinedPeriod, ZonedDateTime.now(), ZonedDateTime.now())
        val errors = QuotaValidator.validate(quota)
        assertThat(errors).containsOnly(QUOTA_NAME_IS_BLANK, UNDEFINED_PERIOD)
    }

}