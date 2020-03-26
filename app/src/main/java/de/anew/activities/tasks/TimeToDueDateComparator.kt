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
import org.threeten.bp.ZonedDateTime

class TimeToDueDateComparator : Comparator<Task> {

    override fun compare(task1: Task?, task2: Task?): Int {
        if (task1 == null || task2 == null) {
            return 0
        }
        val startingFromNow = ZonedDateTime.now()
        val timeDifference = task1.dueIn(startingFromNow).minus(task2.dueIn(startingFromNow))
        return when {
            timeDifference.isZero -> 0
            timeDifference.isNegative -> -1
            else -> 1
        }
    }

}