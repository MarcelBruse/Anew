package de.anew.persistency

import androidx.room.TypeConverter
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

class ZonedDateTimeConverter {

    @TypeConverter
    fun fromZonedDateTime(zonedDateTime: ZonedDateTime?) = zonedDateTime?.toInstant()?.epochSecond

    @TypeConverter
    fun fromEpochSecond(epochSecond: Long?): ZonedDateTime? {
        epochSecond?.let {
            val instant = Instant.ofEpochSecond(epochSecond)
            return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
        }
        return null
    }

}