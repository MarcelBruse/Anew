package de.quotas.models.time

import org.threeten.bp.ZonedDateTime

interface TimeInterval {

    fun includes(instant: ZonedDateTime): Boolean

}