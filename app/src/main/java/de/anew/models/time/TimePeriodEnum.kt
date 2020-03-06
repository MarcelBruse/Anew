package de.anew.models.time

enum class TimePeriodEnum {

    DAILY, WEEKLY, UNDEFINED_PERIOD;

    companion object {

        fun getByTimePeriod(timePeriod: TimePeriod): TimePeriodEnum {
            return when (timePeriod) {
                is Daily -> DAILY
                is Weekly -> WEEKLY
                else -> UNDEFINED_PERIOD
            }
        }

    }
}