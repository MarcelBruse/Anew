package de.quotas.persistency

import de.quotas.models.time.Daily
import de.quotas.models.time.UndefinedPeriod
import de.quotas.models.time.Weekly
import de.quotas.persistency.PeriodConverter.Companion.DAILY
import de.quotas.persistency.PeriodConverter.Companion.UNDEFINED
import de.quotas.persistency.PeriodConverter.Companion.WEEKLY
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.Clock

class PeriodConverterTest {

    private val clock = Clock.systemDefaultZone()

    @Test
    fun convertPeriodToInteger() {
        val periodConverter = PeriodConverter()
        assertThat(periodConverter.fromEnum(UndefinedPeriod)).isEqualTo(UNDEFINED)
        assertThat(periodConverter.fromEnum(Daily(clock))).isEqualTo(DAILY)
        assertThat(periodConverter.fromEnum(Weekly(clock))).isEqualTo(WEEKLY)
    }

    @Test
    fun convertIntegerToPeriod() {
        val periodConverter = PeriodConverter()
        assertThat(periodConverter.fromPeriodCode(UNDEFINED)).isEqualTo(UndefinedPeriod)
        assertThat(periodConverter.fromPeriodCode(DAILY)).isEqualTo(Daily(clock))
        assertThat(periodConverter.fromPeriodCode(WEEKLY)).isEqualTo(Weekly(clock))
    }

}