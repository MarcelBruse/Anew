package de.anew.models.time

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.Clock

class TimePeriodFactoryTest {

    @Test
    fun createDailyPeriod() {
        val timePeriodFactory = TimePeriodFactory(Clock.systemUTC())
        assertThat(timePeriodFactory.dailyPeriod()).isInstanceOf(Daily::class.java)
    }

    @Test
    fun createWeeklyPeriod() {
        val timePeriodFactory = TimePeriodFactory(Clock.systemUTC())
        assertThat(timePeriodFactory.weeklyPeriod()).isInstanceOf(Weekly::class.java)
    }

    @Test
    fun dailyPeriodByEnum() {
        val timePeriodFactory = TimePeriodFactory(Clock.systemUTC())
        val dailyPeriod = timePeriodFactory.timePeriodByEnum(TimePeriodEnum.DAILY)
        assertThat(dailyPeriod).isInstanceOf(Daily::class.java)
    }

    @Test
    fun weeklyPeriodByEnum() {
        val timePeriodFactory = TimePeriodFactory(Clock.systemUTC())
        val weeklyPeriod = timePeriodFactory.timePeriodByEnum(TimePeriodEnum.WEEKLY)
        assertThat(weeklyPeriod).isInstanceOf(Weekly::class.java)
    }

    @Test
    fun undefinedPeriodByEnum() {
        val timePeriodFactory = TimePeriodFactory(Clock.systemUTC())
        val undefinedPeriod = timePeriodFactory.timePeriodByEnum(TimePeriodEnum.UNDEFINED_PERIOD)
        assertThat(undefinedPeriod).isInstanceOf(UndefinedPeriod::class.java)
    }

}