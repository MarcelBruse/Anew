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
import org.threeten.bp.ZonedDateTime

class DailyIntervalTest {

    @Test
    fun clockIsInitialized() {
        val systemClock = Clock.systemUTC()
        val daily = Daily(systemClock)
        assertThat(systemClock).isNotNull
        assertThat(daily.clock()).isEqualTo(systemClock)
    }

    @Test
    fun startOfIntervalIsStartOfToday() {
        val representativeOfInterval = ZonedDateTime.parse("2019-11-16T10:34:24.621+01:00[Europe/Berlin]")
        val expectedStartOfInterval = ZonedDateTime.parse("2019-11-16T00:00:00.000+01:00[Europe/Berlin]")
        val today = Daily().intervalIncluding(representativeOfInterval)
        assertThat(today.startsAt()).isEqualTo(expectedStartOfInterval)
    }

    @Test
    fun startOfNextIntervalIsStartOfNextDay() {
        val representativeOfInterval = ZonedDateTime.parse("2019-11-16T10:34:24.621+01:00[Europe/Berlin]")
        val expectedStartOfNextInterval = ZonedDateTime.parse("2019-11-17T00:00:00.000+01:00[Europe/Berlin]")
        val nextInterval = Daily().intervalIncluding(representativeOfInterval).next()
        assertThat(nextInterval.startsAt()).isEqualTo(expectedStartOfNextInterval)
    }

    @Test
    fun endOfTodayEqualsStartsOfTomorrow() {
        val representativeOfInterval = ZonedDateTime.parse("2020-02-01T19:00:00+01:00[Europe/Berlin]")
        val dayInterval = Daily().intervalIncluding(representativeOfInterval)
        val expectedEndOfInterval = ZonedDateTime.parse("2020-02-02T00:00:00+01:00[Europe/Berlin]")
        assertThat(dayInterval.endsBefore()).isEqualTo(expectedEndOfInterval)
        assertThat(dayInterval.endsBefore()).isEqualTo(dayInterval.next().startsAt())
    }

}