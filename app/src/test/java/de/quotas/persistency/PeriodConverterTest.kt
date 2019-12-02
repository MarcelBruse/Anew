package de.quotas.persistency

import de.quotas.models.time.Daily
import de.quotas.models.time.UndefinedPeriod
import de.quotas.models.time.Weekly
import de.quotas.persistency.PeriodConverter.Companion.DAILY
import de.quotas.persistency.PeriodConverter.Companion.UNDEFINED
import de.quotas.persistency.PeriodConverter.Companion.WEEKLY
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PeriodConverterTest {

    @Test
    fun convertPeriodToInteger() {
        val periodConverter = PeriodConverter()
        assertThat(periodConverter.fromEnum(UndefinedPeriod)).isEqualTo(UNDEFINED)
        assertThat(periodConverter.fromEnum(Daily)).isEqualTo(DAILY)
        assertThat(periodConverter.fromEnum(Weekly)).isEqualTo(WEEKLY)
    }

    @Test
    fun convertIntegerToPeriod() {
        val periodConverter = PeriodConverter()
        assertThat(periodConverter.fromPeriodCode(UNDEFINED)).isEqualTo(UndefinedPeriod)
        assertThat(periodConverter.fromPeriodCode(DAILY)).isEqualTo(Daily)
        assertThat(periodConverter.fromPeriodCode(WEEKLY)).isEqualTo(Weekly)
    }

}