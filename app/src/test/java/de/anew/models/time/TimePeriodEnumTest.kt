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