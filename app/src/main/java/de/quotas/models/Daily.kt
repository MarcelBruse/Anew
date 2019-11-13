package de.quotas.models

class Daily : Period {

    override fun isFulfilled(startTime: Long, lastFulfillmentTime: Long): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun equals(other: Any?): Boolean {
        return other is Daily
    }

    override fun hashCode(): Int {
        return Daily::class.hashCode() + 1
    }

}