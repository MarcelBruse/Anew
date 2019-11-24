package de.quotas.models.time

import org.threeten.bp.Duration
import org.threeten.bp.ZonedDateTime

class WeekInterval(startOfInterval: ZonedDateTime) : Interval(startOfInterval) {

    override fun getDuration(): Duration = Duration.ofDays(7)

}