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
package de.anew.activities.tasks

import androidx.test.ext.junit.runners.AndroidJUnit4
import de.anew.models.task.Task
import de.anew.models.time.Daily
import de.anew.models.time.Weekly
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.Clock
import org.threeten.bp.ZonedDateTime
import java.util.*

@RunWith(AndroidJUnit4::class)
class TimeToDueDateFormatterTest : LocalizationTest() {

    @Test
    fun dueNow() {
        val now = ZonedDateTime.parse("2019-11-16T00:00:00+01:00[Europe/Berlin]")
        val startTime = now.minusDays(3)
        val lastFulfillmentTime = now.minusDays(2)
        val fixedClock = Clock.fixed(now.toInstant(), now.zone)
        val task = Task(0L, "Task", Daily(fixedClock), startTime, lastFulfillmentTime)
        checkFormatOf(task, "Daily · Due in 0 seconds", enEN)
        checkFormatOf(task, "Täglich · Fällig in 0 Sekunden", deDE)
    }

    @Test
    fun dueInTwoMinutes() {
        val now = ZonedDateTime.parse("2019-11-16T23:58:00+01:00[Europe/Berlin]")
        val yesterdayAtTheSameTime = now.minusDays(1)
        val fixedClock = Clock.fixed(now.toInstant(), now.zone)
        val task = Task(0L, "Task", Daily(fixedClock), now, yesterdayAtTheSameTime)
        checkFormatOf(task, "Daily · Due in 2 minutes", enEN)
        checkFormatOf(task, "Täglich · Fällig in 2 Minuten", deDE)
    }

    @Test
    fun overdueSinceOneSecond() {
        val now = ZonedDateTime.parse("2019-11-16T00:00:01+01:00[Europe/Berlin]")
        val startTime = now.minusDays(3)
        val lastFulfillmentTime = now.minusDays(2)
        val fixedClock = Clock.fixed(now.toInstant(), now.zone)
        val task = Task(0L, "Task", Daily(fixedClock), startTime, lastFulfillmentTime)
        checkFormatOf(task, "Daily · Overdue since 1 second", enEN)
        checkFormatOf(task, "Täglich · Überfällig seit 1 Sekunde", deDE)
    }

    @Test
    fun overdueSinceTwoMinutes() {
        val now = ZonedDateTime.parse("2019-11-16T00:02:00+01:00[Europe/Berlin]")
        val startTime = now.minusDays(3)
        val lastFulfillmentTime = now.minusDays(2)
        val fixedClock = Clock.fixed(now.toInstant(), now.zone)
        val task = Task(0L, "Task", Daily(fixedClock), startTime, lastFulfillmentTime)
        checkFormatOf(task, "Daily · Overdue since 2 minutes", enEN)
        checkFormatOf(task, "Täglich · Überfällig seit 2 Minuten", deDE)
    }

    @Test
    fun doneDaily() {
        val now = ZonedDateTime.parse("2019-11-16T00:02:00+01:00[Europe/Berlin]")
        val fixedClock = Clock.fixed(now.toInstant(), now.zone)
        val task = Task(0L, "Task", Daily(fixedClock), now, now.plusMinutes(2))
        checkFormatOf(task, "Daily · Done", enEN)
        checkFormatOf(task, "Täglich · Erledigt", deDE)
    }

    @Test
    fun doneWeekly() {
        val now = ZonedDateTime.parse("2019-11-16T00:02:00+01:00[Europe/Berlin]")
        val fixedClock = Clock.fixed(now.toInstant(), now.zone)
        val task = Task(0L, "Task", Weekly(fixedClock), now, now.plusMinutes(2))
        checkFormatOf(task, "Weekly · Done", enEN)
        checkFormatOf(task, "Wöchentlich · Erledigt", deDE)
    }

    private fun checkFormatOf(task: Task, expected: String, locale: Locale) {
        val context = createNewContextWithLocale(locale)
        assertThat(context).isNotNull
        context?.let {
            val formatDueDate = TimeToDueDateFormatter(it).formatDueDate(task.period, task.isFulfilled(), task.dueIn())
            assertThat(formatDueDate).isEqualTo(expected)
        }
    }

}