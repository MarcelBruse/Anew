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
import de.anew.models.time.TimePeriod
import de.anew.models.time.UndefinedPeriod
import org.threeten.bp.ZonedDateTime

object TaskValidator {

    private const val MAX_TASK_NAME_LENGTH = 40

    fun validateTaskName(taskNameCandidate: String): List<Error> {
        if (taskNameCandidate.isBlank()) {
            return listOf(TASK_NAME_IS_BLANK)
        } else if (taskNameCandidate.length > MAX_TASK_NAME_LENGTH) {
            return listOf(TASK_NAME_LENGTH_EXCEEDS_LIMIT)
        }
        return emptyList()
    }

    fun validatePeriod(periodCandidate: TimePeriod): List<Error> {
        return if (periodCandidate is UndefinedPeriod) listOf(UNDEFINED_PERIOD) else emptyList()
    }

    fun validateLastFulfilmentTime(task: Task, lastFulfillmentTimeCandidate: ZonedDateTime?): List<Error> {
        lastFulfillmentTimeCandidate?.let {
            if (task.startTime.isAfter(it)) {
                return listOf(FULFILLED_BEFORE_START)
            }
        }
        return emptyList()
    }

    fun validate(task: Task): List<Error> {
        val errors = ArrayList<Error>()
        errors.addAll(validateTaskName(task.name))
        errors.addAll(validatePeriod(task.period))
        errors.addAll(
            validateLastFulfilmentTime(
                task,
                task.lastFulfillmentTime
            )
        )
        return errors
    }

    enum class Error {
        TASK_NAME_LENGTH_EXCEEDS_LIMIT,
        TASK_NAME_IS_BLANK,
        UNDEFINED_PERIOD,
        FULFILLED_BEFORE_START
    }

}