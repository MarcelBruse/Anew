package de.quotas.models.time

import org.threeten.bp.Duration
import org.threeten.bp.ZonedDateTime

abstract class Interval(private val startOfInterval: ZonedDateTime) {

    fun includesInstant(zonedDateTime: ZonedDateTime): Boolean {
        val duration = Duration.between(startOfInterval, zonedDateTime)
        return Duration.ZERO <= duration && duration < getDuration()
    }

    abstract fun getDuration(): Duration

}