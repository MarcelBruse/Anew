package de.quotas.persistency

import androidx.room.TypeConverter
import de.quotas.models.time.Daily
import de.quotas.models.time.Period
import de.quotas.models.time.UndefinedPeriod
import de.quotas.models.time.Weekly

class PeriodConverter {

    @TypeConverter
    fun fromEnum(period: Period): Int {
        return when (period) {
            is Daily -> DAILY
            is Weekly -> WEEKLY
            else -> UNDEFINED
        }
    }

    @TypeConverter
    fun fromPeriodCode(periodCode: Int): Period {
        return when (periodCode) {
            DAILY -> Daily
            WEEKLY -> Weekly
            else -> UndefinedPeriod
        }
    }

    companion object {
        const val UNDEFINED = -1
        const val DAILY = 1
        const val WEEKLY = 2
    }

}