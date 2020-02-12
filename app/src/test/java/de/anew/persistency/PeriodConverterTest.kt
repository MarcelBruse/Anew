package de.anew.persistency

import de.anew.models.time.Daily
import de.anew.models.time.TimePeriodEnum.*
import de.anew.models.time.UndefinedPeriod
import de.anew.models.time.Weekly
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.Clock

class PeriodConverterTest {

    private val clock = Clock.systemDefaultZone()

    @Test
    fun convertPeriodToInteger() {
        val periodConverter = PeriodConverter()
        assertThat(periodConverter.fromEnum(UndefinedPeriod)).isEqualTo(UNDEFINED_PERIOD.ordinal)
        assertThat(periodConverter.fromEnum(Daily(clock))).isEqualTo(DAILY.ordinal)
        assertThat(periodConverter.fromEnum(Weekly(clock))).isEqualTo(WEEKLY.ordinal)
    }

    @Test
    fun convertIntegerToPeriod() {
        val periodConverter = PeriodConverter()
        assertThat(periodConverter.fromPeriodCode(UNDEFINED_PERIOD.ordinal)).isEqualTo(UndefinedPeriod)
        assertThat(periodConverter.fromPeriodCode(DAILY.ordinal)).isEqualTo(Daily(clock))
        assertThat(periodConverter.fromPeriodCode(WEEKLY.ordinal)).isEqualTo(Weekly(clock))
    }

}