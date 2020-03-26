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

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.ZonedDateTime

class ZonedDateTimeConverterTest {

    @Test
    fun fromZonedDateTime() {
        val now = ZonedDateTime.now()
        val epochSecond = now.toInstant().epochSecond
        assertThat(ZonedDateTimeConverter().fromZonedDateTime(now)).isEqualTo(epochSecond)
    }

    @Test
    fun fromNullZonedDateTime() {
        assertThat(ZonedDateTimeConverter().fromZonedDateTime(null)).isEqualTo(null)
    }

    @Test
    fun fromEpochSecond() {
        val zonedDateTime = ZonedDateTime.parse("2020-01-16T22:19:25+01:00[Europe/Berlin]")
        val epochSecond = zonedDateTime.toInstant().epochSecond
        assertThat(ZonedDateTimeConverter().fromEpochSecond(epochSecond)).isEqualTo(zonedDateTime)
    }

    @Test
    fun fromNullEpochSecond() {
        assertThat(ZonedDateTimeConverter().fromEpochSecond(null)).isEqualTo(null)
    }

}