package de.quotas.models

class UndefinedPeriod : Period {

    override fun isFulfilled(startTime: Long, lastFulfillmentTime: Long): Boolean {
        return false
    }

    override fun equals(other: Any?): Boolean {
        return other is UndefinedPeriod
    }

    override fun hashCode(): Int {
        return UndefinedPeriod::class.hashCode() + 1
    }

}