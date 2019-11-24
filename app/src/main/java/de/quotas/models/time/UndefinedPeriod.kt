package de.quotas.models.time

import org.threeten.bp.ZonedDateTime

object UndefinedPeriod : Period() {

    override fun getIntervalFromRepresentative(representative: ZonedDateTime): Interval {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun equals(other: Any?): Boolean {
        return other is UndefinedPeriod
    }

    override fun hashCode(): Int {
        return UndefinedPeriod::class.hashCode() + 1
    }

}