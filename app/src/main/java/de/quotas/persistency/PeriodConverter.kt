package de.quotas.persistency

import androidx.room.TypeConverter
import de.quotas.models.time.Daily
import de.quotas.models.time.TimePeriod
import de.quotas.models.time.UndefinedPeriod
import de.quotas.models.time.Weekly
import org.threeten.bp.Clock

class PeriodConverter {

    @TypeConverter
    fun fromEnum(period: TimePeriod): Int {
        return when (period) {
            is Daily -> DAILY
            is Weekly -> WEEKLY
            else -> UNDEFINED
        }
    }

    @TypeConverter
    fun fromPeriodCode(periodCode: Int): TimePeriod {
        return when (periodCode) {
            DAILY -> Daily(Clock.systemDefaultZone())
            WEEKLY -> Weekly(Clock.systemDefaultZone())
            else -> UndefinedPeriod
        }
    }

    companion object {
        const val UNDEFINED = -1
        const val DAILY = 1
        const val WEEKLY = 2
    }

}