package de.quotas.models.time

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class TimePeriodFactoryTest {

    @Test
    fun createDailyPeriod() {
        assertThat(TimePeriodFactory.dailyPeriod()).isInstanceOf(Daily::class.java)
    }

    @Test
    fun createWeeklyPeriod() {
        assertThat(TimePeriodFactory.weeklyPeriod()).isInstanceOf(Weekly::class.java)
    }

}