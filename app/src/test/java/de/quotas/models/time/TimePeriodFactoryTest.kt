package de.quotas.models.time

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

}