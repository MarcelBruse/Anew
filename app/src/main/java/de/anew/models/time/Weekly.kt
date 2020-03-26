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
package de.anew.models.time

import org.threeten.bp.Clock
import org.threeten.bp.DayOfWeek
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.temporal.ChronoUnit
import org.threeten.bp.temporal.TemporalAdjusters

class Weekly(private val clock: Clock) : TimePeriod {

    constructor(): this(Clock.systemDefaultZone())

    override fun clock() = clock

    override fun currentInterval() = intervalIncluding(ZonedDateTime.now(clock))

    override fun intervalIncluding(representative: ZonedDateTime): TimeInterval {
        val monday = representative.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val startOfWeek = monday.truncatedTo(ChronoUnit.DAYS)
        return WeekInterval(startOfWeek)
    }

    override fun currentIntervalIncludes(instant: ZonedDateTime): Boolean {
        return currentInterval().includes(instant)
    }

    override fun equals(other: Any?): Boolean {
        return other is Weekly && other.clock == clock
    }

    override fun hashCode(): Int {
        return Weekly::class.hashCode() + clock::class.hashCode()
    }

}