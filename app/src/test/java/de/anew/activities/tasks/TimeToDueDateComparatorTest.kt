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

import de.anew.models.task.Task
import de.anew.models.time.Daily
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.ZonedDateTime

class TimeToDueDateComparatorTest {

    @Test
    fun firstTaskIsDueBeforeSecondTask() {
        val now = ZonedDateTime.now()
        val task1 = Task(0L, "Task1", Daily(), now.minusDays(2), null)
        val task2 = Task(1L, "Task2", Daily(), now, null)
        assertThat(TimeToDueDateComparator().compare(task1, task2)).isNegative()
    }

    @Test
    fun firstTaskIsDueAfterSecondTask() {
        val now = ZonedDateTime.now()
        val task1 = Task(0L, "Task1", Daily(), now, null)
        val task2 = Task(1L, "Task2", Daily(), now.minusDays(2), null)
        assertThat(TimeToDueDateComparator().compare(task1, task2)).isPositive()
    }

    @Test
    fun firstAndSecondTaskAreDueAtTheSameTime() {
        val now = ZonedDateTime.now()
        val task1 = Task(0L, "Task1", Daily(), now, null)
        val task2 = Task(1L, "Task2", Daily(), now, null)
        assertThat(TimeToDueDateComparator().compare(task1, task2)).isEqualTo(0)
    }

    @Test
    fun tasksAreNull() {
        assertThat(TimeToDueDateComparator().compare(null, null)).isEqualTo(0)
    }

}