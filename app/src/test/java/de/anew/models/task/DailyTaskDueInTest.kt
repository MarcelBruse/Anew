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
package de.anew.models.task

import de.anew.models.time.Daily
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.*

class DailyTaskDueInTest {

    @Test
    fun twoHoursUntilDueDateOfYetUnfulfilledDailyTask() {
        val fixedTime = Instant.parse("2020-01-29T22:00:00.000Z")
        val fixedClock = Clock.fixed(fixedTime, ZoneId.of("Europe/London"))
        val now = ZonedDateTime.now(fixedClock)
        val startTime = now.minusHours(1)
        val task =
            Task(0, "", Daily(fixedClock), startTime, null)
        assertThat(task.dueIn()).isEqualTo(Duration.ofHours(2))
    }

    @Test
    fun timeUntilDueDateOfDailyTaskFulfilledYesterday() {
        val fixedTime = Instant.parse("2020-01-29T20:00:00.000Z")
        val fixedClock = Clock.fixed(fixedTime, ZoneId.of("Europe/London"))
        val now = ZonedDateTime.now(fixedClock)
        val startTime = now.minusDays(10)
        val lastFulfillmentTime = now.minusDays(1)
        val task = Task(
            0,
            "",
            Daily(fixedClock),
            startTime,
            lastFulfillmentTime
        )
        assertThat(task.dueIn()).isEqualTo(Duration.ofHours(4))
    }

    @Test
    fun timeUntilDueDateOfDailyTaskFulfilledToday() {
        val fixedTime = Instant.parse("2020-01-29T20:00:00.000Z")
        val fixedClock = Clock.fixed(fixedTime, ZoneId.of("Europe/London"))
        val now = ZonedDateTime.now(fixedClock)
        val startTime = now.minusDays(10)
        val lastFulfillmentTime = now.minusHours(1)
        val task = Task(
            0,
            "",
            Daily(fixedClock),
            startTime,
            lastFulfillmentTime
        )
        assertThat(task.dueIn()).isEqualTo(Duration.ofHours(28))
    }

    @Test
    fun dayTimeSavingTwentyThreeHourDay() {
        val fixedTime = ZonedDateTime.parse("2020-03-29T00:00+01:00[Europe/Berlin]")
        val fixedClock = Clock.fixed(fixedTime.toInstant(), ZoneId.of("Europe/Berlin"))
        val startTime = ZonedDateTime.now(fixedClock).minusDays(5)
        val lastFulfillmentTime = ZonedDateTime.now(fixedClock).minusHours(10)
        val task = Task(
            0,
            "",
            Daily(fixedClock),
            startTime,
            lastFulfillmentTime
        )
        assertThat(task.dueIn()).isEqualTo(Duration.ofHours(23))
    }


    @Test
    fun dayTimeSavingTwentyFiveHourDay() {
        val fixedTime = ZonedDateTime.parse("2020-10-25T00:00:00+02:00[Europe/Berlin]")
        val fixedClock = Clock.fixed(fixedTime.toInstant(), ZoneId.of("Europe/Berlin"))
        val startTime = ZonedDateTime.now(fixedClock).minusDays(5)
        val lastFulfillmentTime = ZonedDateTime.now(fixedClock).minusHours(10)
        val task = Task(
            0,
            "",
            Daily(fixedClock),
            startTime,
            lastFulfillmentTime
        )
        assertThat(task.dueIn()).isEqualTo(Duration.ofHours(25))
    }

}