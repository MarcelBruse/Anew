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

import de.anew.models.task.TaskValidator.Error.*
import de.anew.models.time.Daily
import de.anew.models.time.UndefinedPeriod
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.ZonedDateTime

class TaskValidatorTest {

    @Test
    fun validTaskNameWithMaximumLength() {
        val errors = TaskValidator.validateTaskName("1234567890123456789012345678901234567890")
        assertThat(errors).isEmpty()
    }

    @Test
    fun invalidTaskNameExceedsMaximumLength() {
        val errors = TaskValidator.validateTaskName("12345678901234567890123456789012345678901")
        assertThat(errors).hasSize(1)
        assertThat(errors).containsOnly(TASK_NAME_LENGTH_EXCEEDS_LIMIT)
    }

    @Test
    fun invalidEmptyTaskName() {
        val errors = TaskValidator.validateTaskName("")
        assertThat(errors).hasSize(1)
        assertThat(errors).containsOnly(TASK_NAME_IS_BLANK)
    }

    @Test
    fun invalidBlankTaskName() {
        val errors = TaskValidator.validateTaskName(" ")
        assertThat(errors).containsOnly(TASK_NAME_IS_BLANK)
    }

    @Test
    fun validPeriod() {
        val errors = TaskValidator.validatePeriod(Daily())
        assertThat(errors).isEmpty()
    }

    @Test
    fun invalidUndefinedPeriod() {
        val errors = TaskValidator.validatePeriod(UndefinedPeriod)
        assertThat(errors).containsOnly(UNDEFINED_PERIOD)
    }

    @Test
    fun validTask() {
        val task = Task(
            0L,
            "Some task",
            Daily(),
            ZonedDateTime.now(),
            ZonedDateTime.now()
        )
        val errors = TaskValidator.validate(task)
        assertThat(errors).isEmpty()
    }

    @Test
    fun invalidTask() {
        val task = Task(
            0L,
            "",
            UndefinedPeriod,
            ZonedDateTime.now(),
            ZonedDateTime.now()
        )
        val errors = TaskValidator.validate(task)
        assertThat(errors).containsOnly(TASK_NAME_IS_BLANK, UNDEFINED_PERIOD)
    }

}