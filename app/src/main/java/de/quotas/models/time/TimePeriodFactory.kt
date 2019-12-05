package de.quotas.models.time

import org.threeten.bp.Clock

class TimePeriodFactory(private val clock: Clock) {

    fun dailyPeriod() = Daily(clock)

    fun weeklyPeriod() = Weekly(clock)

}