package de.anew.models.time

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class TimePeriodEnumTest {

    @Test
    fun getValueByDailyPeriod() {
        val valueByTimePeriod = TimePeriodEnum.getByTimePeriod(Daily())
        assertThat(valueByTimePeriod).isEqualTo(TimePeriodEnum.DAILY)
    }

    @Test
    fun getValueByWeeklyPeriod() {
        val valueByTimePeriod = TimePeriodEnum.getByTimePeriod(Weekly())
        assertThat(valueByTimePeriod).isEqualTo(TimePeriodEnum.WEEKLY)
    }

    @Test
    fun getValueByUndefinedPeriod() {
        val valueByTimePeriod = TimePeriodEnum.getByTimePeriod(UndefinedPeriod)
        assertThat(valueByTimePeriod).isEqualTo(TimePeriodEnum.UNDEFINED_PERIOD)
    }

}