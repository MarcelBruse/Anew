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
import org.threeten.bp.ZonedDateTime

class TaskFulfillmentTest {

    @Test
    fun taskNeverFulfilled() {
        val task =
            Task(0L, "Task", Daily(),
                START_TIME, null)
        assertThat(task.isFulfilled()).isFalse()
    }

    @Test
    fun taskFulfilled() {
        val task = Task(
            0L,
            "Task",
            Daily(),
            START_TIME,
            ZonedDateTime.now()
        )
        assertThat(task.isFulfilled()).isTrue()
    }

    @Test
    fun taskNotFulfilled() {
        val task = Task(
            0L,
            "Task",
            Daily(),
            START_TIME,
            ZonedDateTime.now().minusDays(2)
        )
        assertThat(task.isFulfilled()).isFalse()
    }

    companion object {

        val START_TIME: ZonedDateTime = ZonedDateTime.parse("2019-11-16T10:34:24.621+01:00[Europe/Berlin]")

    }

}