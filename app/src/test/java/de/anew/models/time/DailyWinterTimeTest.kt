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
import org.threeten.bp.ZonedDateTime

class DailyWinterTimeTest {

    @Test
    fun endOfDayBeforeLastSummerTimeDayIsNotIncluded() {
        val lastSummerTimeDayEnd = ZonedDateTime.parse("2020-10-24T23:59:59+02:00[Europe/Berlin]")

        val summerRepresentative = ZonedDateTime.parse("2020-10-25T01:30:00+02:00[Europe/Berlin]")
        val dayFromSummerRepresentative = Daily().intervalIncluding(summerRepresentative)
        assertThat(dayFromSummerRepresentative.includes(lastSummerTimeDayEnd)).isFalse()

        val winterRepresentative = ZonedDateTime.parse("2020-10-25T10:30:00+01:00[Europe/Berlin]")
        val dayFromWinterRepresentative = Daily().intervalIncluding(winterRepresentative)
        assertThat(dayFromWinterRepresentative.includes(lastSummerTimeDayEnd)).isFalse()
    }

    @Test
    fun startOfLastSummerTimeDayIsIncluded() {
        val lastSummerTimeDayStart = ZonedDateTime.parse("2020-10-25T00:00+02:00[Europe/Berlin]")

        val summerRepresentative = ZonedDateTime.parse("2020-10-25T01:30:00+02:00[Europe/Berlin]")
        val dayFromSummerRepresentative = Daily().intervalIncluding(summerRepresentative)
        assertThat(dayFromSummerRepresentative.includes(lastSummerTimeDayStart)).isTrue()

        val winterRepresentative = ZonedDateTime.parse("2020-10-25T15:30:30+01:00[Europe/Berlin]")
        val dayFromWinterRepresentative = Daily().intervalIncluding(winterRepresentative)
        assertThat(dayFromWinterRepresentative.includes(lastSummerTimeDayStart)).isTrue()
    }

    @Test
    fun endOfFirstWinterTimeDayIsIncluded() {
        val endOfFirstWinterTimeDay = ZonedDateTime.parse("2020-10-25T23:59:59+01:00[Europe/Berlin]")

        val summerRepresentative = ZonedDateTime.parse("2020-10-25T01:30:00+02:00[Europe/Berlin]")
        val dayFromSummerRepresentative = Daily().intervalIncluding(summerRepresentative)
        assertThat(dayFromSummerRepresentative.includes(endOfFirstWinterTimeDay)).isTrue()

        val winterRepresentative = ZonedDateTime.parse("2020-10-25T16:30:00+01:00[Europe/Berlin]")
        val dayFromWinterRepresentative = Daily().intervalIncluding(winterRepresentative)
        assertThat(dayFromWinterRepresentative.includes(endOfFirstWinterTimeDay)).isTrue()
    }

    @Test
    fun startOfDayAfterFirstWinterTimeDayIsNotIncluded() {
        val startOfDayAfterFirstWinterTimeDay = ZonedDateTime.parse("2020-10-26T00:00:00+01:00[Europe/Berlin]")

        val summerRepresentative = ZonedDateTime.parse("2020-10-25T01:30:00+02:00[Europe/Berlin]")
        val dayFromSummerRepresentative = Daily().intervalIncluding(summerRepresentative)
        assertThat(dayFromSummerRepresentative.includes(startOfDayAfterFirstWinterTimeDay)).isFalse()

        val winterRepresentative = ZonedDateTime.parse("2020-10-25T17:30:00+01:00[Europe/Berlin]")
        val dayFromWinterRepresentative = Daily().intervalIncluding(winterRepresentative)
        assertThat(dayFromWinterRepresentative.includes(startOfDayAfterFirstWinterTimeDay)).isFalse()
    }

    @Test
    fun nextIntervalStartsInWinterTime() {
        val lastSummerTimeDayStart = ZonedDateTime.parse("2020-10-25T00:00+02:00[Europe/Berlin]")
        val firstWinterTimeDayStart = ZonedDateTime.parse("2020-10-26T00:00+01:00[Europe/Berlin]")
        val summerTimeDay = Daily().intervalIncluding(lastSummerTimeDayStart)
        assertThat(summerTimeDay.next().startsAt()).isEqualTo(firstWinterTimeDayStart)
    }

}