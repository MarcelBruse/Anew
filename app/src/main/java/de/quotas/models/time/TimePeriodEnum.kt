package de.quotas.models.time

enum class TimePeriodEnum {

    DAILY, WEEKLY, UNDEFINED_PERIOD;

    companion object {

        fun getValueByTimePeriod(timePeriod: TimePeriod): TimePeriodEnum {
            return when (timePeriod) {
                is Daily -> DAILY
                is Weekly -> WEEKLY
                else -> UNDEFINED_PERIOD
            }
        }

    }
}