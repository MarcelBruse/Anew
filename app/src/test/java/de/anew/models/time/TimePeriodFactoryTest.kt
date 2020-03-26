/*
 * Copyright 2020 Marcel Bruse
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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