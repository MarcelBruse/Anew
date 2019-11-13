package de.quotas.models

import de.quotas.models.PeriodConverter.Companion.DAILY
import de.quotas.models.PeriodConverter.Companion.UNDEFINED
import de.quotas.models.PeriodConverter.Companion.WEEKLY
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PeriodConverterTest {

    @Test
    fun convertPeriodToInteger() {
        val periodConverter = PeriodConverter()
        assertThat(periodConverter.fromEnum(UndefinedPeriod())).isEqualTo(UNDEFINED)
        assertThat(periodConverter.fromEnum(Daily())).isEqualTo(DAILY)
        assertThat(periodConverter.fromEnum(Weekly())).isEqualTo(WEEKLY)
    }

    @Test
    fun convertIntegerToPeriod() {
        val periodConverter = PeriodConverter()
        assertThat(periodConverter.fromPeriodCode(UNDEFINED)).isEqualTo(UndefinedPeriod())
        assertThat(periodConverter.fromPeriodCode(DAILY)).isEqualTo(Daily())
        assertThat(periodConverter.fromPeriodCode(WEEKLY)).isEqualTo(Weekly())
    }

}