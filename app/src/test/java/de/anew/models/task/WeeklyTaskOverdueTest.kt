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

import de.anew.models.time.Weekly
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.*

class WeeklyTaskOverdueTest {

    @Test
    fun twoDaysSinceDueDateOfYetUnfulfilledDailyTask() {
        val fixedTime = Instant.parse("2020-01-29T00:00:00.000Z")
        val fixedClock = Clock.fixed(fixedTime, ZoneId.of("Europe/London"))
        val now = ZonedDateTime.now(fixedClock)
        val startTime = now.minusWeeks(1)
        val task =
            Task(0, "", Weekly(fixedClock), startTime, null)
        assertThat(task.dueIn().abs()).isEqualTo(Duration.ofDays(2))
        assertThat(task.dueIn().isNegative).isTrue()
    }

    @Test
    fun twoDaysSinceDueDateOfFulfilledDailyTask() {
        val fixedTime = Instant.parse("2020-01-29T00:00:00.000Z")
        val fixedClock = Clock.fixed(fixedTime, ZoneId.of("Europe/London"))
        val now = ZonedDateTime.now(fixedClock)
        val startTime = now.minusWeeks(20)
        val lastFulfillmentTime = now.minusWeeks(2)
        val task = Task(
            0,
            "",
            Weekly(fixedClock),
            startTime,
            lastFulfillmentTime
        )
        assertThat(task.dueIn().abs()).isEqualTo(Duration.ofDays(2))
        assertThat(task.dueIn().isNegative).isTrue()
    }

}