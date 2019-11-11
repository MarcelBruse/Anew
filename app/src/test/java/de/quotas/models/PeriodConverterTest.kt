package de.quotas.models

import de.quotas.models.Quota.Period.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PeriodConverterTest {

    @Test
    fun convertPeriodToInteger() {
        val periodConverter = PeriodConverter()
        assertThat(periodConverter.fromEnum(UNDEFINED)).isEqualTo(UNDEFINED.periodCode)
        assertThat(periodConverter.fromEnum(DAILY)).isEqualTo(DAILY.periodCode)
        assertThat(periodConverter.fromEnum(WEEKLY)).isEqualTo(WEEKLY.periodCode)
    }

    @Test
    fun convertIntegerToPeriod() {
        val periodConverter = PeriodConverter()
        assertThat(periodConverter.fromPeriodCode(UNDEFINED.periodCode)).isEqualTo(UNDEFINED)
        assertThat(periodConverter.fromPeriodCode(DAILY.periodCode)).isEqualTo(DAILY)
        assertThat(periodConverter.fromPeriodCode(WEEKLY.periodCode)).isEqualTo(WEEKLY)
    }

}