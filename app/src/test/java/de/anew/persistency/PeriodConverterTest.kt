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