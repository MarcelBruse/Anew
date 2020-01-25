package de.quotas.models.time

import org.threeten.bp.ZonedDateTime

interface TimeInterval {

    fun start(): ZonedDateTime

    fun next(): TimeInterval

    fun includes(instant: ZonedDateTime): Boolean

}