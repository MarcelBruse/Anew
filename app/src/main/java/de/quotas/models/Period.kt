package de.quotas.models

interface Period {

    fun isFulfilled(startTime: Long, lastFulfillmentTime: Long): Boolean

}