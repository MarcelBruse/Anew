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
package de.anew.persistency

import androidx.room.TypeConverter
import de.anew.models.time.*

class PeriodConverter {

    @TypeConverter
    fun fromEnum(period: TimePeriod): Int {
        return when (period) {
            is Daily -> TimePeriodEnum.DAILY.ordinal
            is Weekly -> TimePeriodEnum.WEEKLY.ordinal
            else -> TimePeriodEnum.UNDEFINED_PERIOD.ordinal
        }
    }

    @TypeConverter
    fun fromPeriodCode(periodCode: Int): TimePeriod {
        return when (periodCode) {
            TimePeriodEnum.DAILY.ordinal -> Daily()
            TimePeriodEnum.WEEKLY.ordinal -> Weekly()
            else -> UndefinedPeriod
        }
    }

}