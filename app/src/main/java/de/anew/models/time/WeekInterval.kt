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

import org.threeten.bp.Period
import org.threeten.bp.ZonedDateTime

class WeekInterval(private val startOfWeek: ZonedDateTime) : TimeInterval {

    private val oneWeek = Period.ofWeeks(1)

    override fun startsAt() = startOfWeek

    override fun endsBefore() = next().startOfWeek

    override fun next() = WeekInterval(startOfWeek + oneWeek)

    override fun includes(instant: ZonedDateTime): Boolean {
        val fromStartToInstant = Period.between(startOfWeek.toLocalDate(), instant.toLocalDate())
        val difference = oneWeek.minus(fromStartToInstant)
        return !(fromStartToInstant.isNegative || difference.isNegative || difference.isZero)
    }

}