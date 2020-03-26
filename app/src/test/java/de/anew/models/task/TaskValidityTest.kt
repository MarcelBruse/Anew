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
import de.anew.models.time.UndefinedPeriod
import de.anew.models.time.Weekly
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.ZonedDateTime

class TaskValidityTest {

    @Test
    fun invalidTaskWithEmptyName() {
        val task = Task(
            0L,
            "",
            Daily(),
            START_TIME,
            AFTER_START_TIME
        )
        assertThat(task.isValid()).isFalse()
    }

    @Test
    fun invalidTaskWithBlankName() {
        val task = Task(
            0L,
            " ",
            Daily(),
            START_TIME,
            AFTER_START_TIME
        )
        assertThat(task.isValid()).isFalse()
    }

    @Test
    fun validTaskWithNullFulfilmentTime() {
        val task =
            Task(0L, "Task", Weekly(),
                START_TIME, null)
        assertThat(task.isValid()).isTrue()
    }

    @Test
    fun invalidTaskWithUndefinedPeriod() {
        val task = Task(
            0L,
            "Task",
            UndefinedPeriod,
            START_TIME,
            AFTER_START_TIME
        )
        assertThat(task.isValid()).isFalse()
    }

    @Test
    fun invalidTaskWithStartAfterFulfilmentTime() {
        val task = Task(
            0L,
            "Task",
            Daily(),
            START_TIME.plusSeconds(1),
            START_TIME
        )
        assertThat(task.isValid()).isFalse()
    }

    @Test
    fun validTaskWithSameStartAndFulfilmentTime() {
        val task =
            Task(0L, "Task", Daily(),
                START_TIME,
                START_TIME
            )
        assertThat(task.isValid()).isTrue()
    }

    @Test
    fun validTaskWithStartTimeBeforeFulfilmentTime() {
        val task = Task(
            0L,
            "Task",
            Daily(),
            START_TIME,
            AFTER_START_TIME
        )
        assertThat(task.isValid()).isTrue()
    }

    @Test
    fun nameSetterKeepsInvariants() {
        val task = Task(
            0L,
            "Task",
            Daily(),
            START_TIME,
            AFTER_START_TIME
        )
        task.name = ""
        assertThat(task.name).isEqualTo("Task")
        task.name = " "
        assertThat(task.name).isEqualTo("Task")
        task.lastFulfillmentTime = START_TIME.minusSeconds(1)
        assertThat(task.lastFulfillmentTime).isEqualTo(AFTER_START_TIME)
        task.period = UndefinedPeriod
        assertThat(task.period).isEqualTo(Daily())
    }

    @Test
    fun nameSetterAcceptsValidValue() {
        val task = Task(
            0L,
            "Task",
            Daily(),
            START_TIME,
            AFTER_START_TIME
        )
        task.name = task.name.reversed()
        assertThat(task.name).isEqualTo("Task".reversed())
    }

    @Test
    fun lastFulfillmentTimeSetterKeepsInvariants() {
        val task = Task(
            0L,
            "Task",
            Daily(),
            START_TIME,
            AFTER_START_TIME
        )
        task.lastFulfillmentTime = START_TIME.minusSeconds(1)
        assertThat(task.lastFulfillmentTime).isEqualTo(AFTER_START_TIME)
    }

    @Test
    fun lastFulfillmentTimeSetterAcceptsValidValue() {
        val task = Task(
            0L,
            "Task",
            Daily(),
            START_TIME,
            AFTER_START_TIME
        )
        task.lastFulfillmentTime =
            START_TIME
        assertThat(task.lastFulfillmentTime).isEqualTo(START_TIME)
        task.lastFulfillmentTime = START_TIME.plusDays(1)
        assertThat(task.lastFulfillmentTime).isEqualTo(START_TIME.plusDays(1))
    }

    @Test
    fun periodSetterKeepsInvariants() {
        val task = Task(
            0L,
            "Task",
            Daily(),
            START_TIME,
            AFTER_START_TIME
        )
        task.period = UndefinedPeriod
        assertThat(task.period).isEqualTo(Daily())
    }

    @Test
    fun periodSetterAcceptsValidValue() {
        val task = Task(
            0L,
            "Task",
            Daily(),
            START_TIME,
            AFTER_START_TIME
        )
        task.period = Weekly()
        assertThat(task.period).isEqualTo(Weekly())
    }

    companion object {

        val START_TIME: ZonedDateTime = ZonedDateTime.parse("2019-11-16T10:34:24.621+01:00[Europe/Berlin]")

        val AFTER_START_TIME: ZonedDateTime = START_TIME.plusMinutes(1)

    }

}