package de.quotas.persistency

import androidx.room.TypeConverter
import de.quotas.models.time.*

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