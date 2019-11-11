package de.quotas.models

import androidx.room.TypeConverter

class PeriodConverter {

    @TypeConverter
    fun fromEnum(period: Quota.Period): Int {
        return period.periodCode
    }

    @TypeConverter
    fun fromPeriodCode(periodCode: Int): Quota.Period {
        return when (periodCode) {
            1 -> Quota.Period.DAILY
            2 -> Quota.Period.WEEKLY
            else -> Quota.Period.UNDEFINED
        }
    }

}