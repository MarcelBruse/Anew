package de.anew.models.time

import org.threeten.bp.Clock

class TimePeriodFactory(private val clock: Clock) {

    fun dailyPeriod() = Daily(clock)

    fun weeklyPeriod() = Weekly(clock)

    fun timePeriodByEnum(timePeriodEnum: TimePeriodEnum): TimePeriod {
        return when (timePeriodEnum) {
            TimePeriodEnum.DAILY -> Daily(clock)
            TimePeriodEnum.WEEKLY -> Weekly(clock)
            else -> UndefinedPeriod
        }
    }

}