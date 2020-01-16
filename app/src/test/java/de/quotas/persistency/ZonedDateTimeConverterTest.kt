package de.quotas.persistency

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