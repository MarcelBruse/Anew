package de.quotas.models

class Weekly : Period {

    override fun isFulfilled(startTime: Long, lastFulfillmentTime: Long): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun equals(other: Any?): Boolean {
        return other is Weekly
    }

    override fun hashCode(): Int {
        return Weekly::class.hashCode() + 1
    }

}