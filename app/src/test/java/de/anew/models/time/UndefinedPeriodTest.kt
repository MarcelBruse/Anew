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

import org.junit.Test
import org.threeten.bp.ZonedDateTime

class UndefinedPeriodTest {

    @Test(expected = UnsupportedOperationException::class)
    fun clockIsUnsupported() {
        UndefinedPeriod.clock()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun getCurrentIntervalIsUnsupported() {
        UndefinedPeriod.currentInterval()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun getIntervalContainingIsUnsupported() {
        UndefinedPeriod.intervalIncluding(ZonedDateTime.now())
    }

    @Test(expected = UnsupportedOperationException::class)
    fun currentIntervalIncludesIsUnsupported() {
        UndefinedPeriod.currentIntervalIncludes(ZonedDateTime.now())
    }

}