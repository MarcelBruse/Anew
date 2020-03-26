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

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import de.anew.models.time.TimePeriod
import de.anew.persistency.PeriodConverter
import de.anew.persistency.ZonedDateTimeConverter
import org.threeten.bp.Duration
import org.threeten.bp.ZonedDateTime

@Entity
@TypeConverters(PeriodConverter::class, ZonedDateTimeConverter::class)
class Task(
    @PrimaryKey(autoGenerate = true) val id: Long,
    name: String,
    period: TimePeriod,
    val startTime: ZonedDateTime,
    lastFulfillmentTime: ZonedDateTime?
) {

    var name: String = name
        set(name) {
            if (TaskValidator.validateTaskName(name).isEmpty()) {
                field = name
            }
        }

    var period: TimePeriod = period
        set(period) {
            if (TaskValidator.validatePeriod(period).isEmpty()) {
                field = period
            }
        }

    var lastFulfillmentTime: ZonedDateTime? = lastFulfillmentTime
        set(lastFulfillmentTime) {
            if (TaskValidator.validateLastFulfilmentTime(
                    this,
                    lastFulfillmentTime
                ).isEmpty()) {
                field = lastFulfillmentTime
            }
        }

    fun isFulfilled(): Boolean {
        lastFulfillmentTime?.let {
            return period.currentIntervalIncludes(it)
        }
        return false
    }

    fun dueDate(): ZonedDateTime {
        lastFulfillmentTime?.let {
            return period.intervalIncluding(it).next().endsBefore()
        }
        return period.intervalIncluding(startTime).endsBefore()

    }

    fun dueIn() = dueIn(ZonedDateTime.now(period.clock()))

    fun dueIn(startingFrom: ZonedDateTime): Duration = Duration.between(startingFrom, dueDate())

    fun isValid() = TaskValidator.validate(this).isEmpty()

}