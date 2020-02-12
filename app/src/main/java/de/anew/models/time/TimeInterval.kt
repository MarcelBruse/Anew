package de.anew.models.time

import org.threeten.bp.ZonedDateTime

interface TimeInterval {

    fun startsAt(): ZonedDateTime

    fun endsBefore(): ZonedDateTime

    fun next(): TimeInterval

    fun includes(instant: ZonedDateTime): Boolean

}