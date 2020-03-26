/*
 * Copyright 2020 Marcel Bruse
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.anew.activities.tasks

import android.content.Context
import de.anew.R
import org.threeten.bp.Duration

class DurationFormatter(context: Context) {

    private val seconds = context.getString(R.string.seconds)

    private val oneSecond = context.getString(R.string.one_second)

    private val minutes = context.getString(R.string.minutes)

    private val oneMinute = context.getString(R.string.one_minute)

    private val hours = context.getString(R.string.hours)

    private val oneHour = context.getString(R.string.one_hour)

    private val days = context.getString(R.string.days)

    private val oneDay = context.getString(R.string.one_day)

    private val weeks = context.getString(R.string.weeks)

    private val oneWeek = context.getString(R.string.one_week)

    private val conjunction = context.getString(R.string.and)

    fun format(duration: Duration): String {
        val absoluteDuration = duration.abs()
        return when {
            absoluteDuration < Duration.ofMinutes(1) -> formatSecondsIncludingZero(absoluteDuration)
            absoluteDuration < Duration.ofHours(1) -> formatMinutesAndSeconds(absoluteDuration)
            absoluteDuration < Duration.ofDays(1) -> formatHoursAndMinutes(absoluteDuration)
            absoluteDuration < Duration.ofDays(7) -> formatDaysAndHours(absoluteDuration)
            else -> formatWeeksAndDays(absoluteDuration)
        }
    }

    private fun formatSecondsIncludingZero(duration: Duration): String {
        return if (duration.isZero) "${duration.seconds} $seconds" else  formatSeconds(duration)
    }

    private fun formatSeconds(duration: Duration): String {
        return when {
            duration <= Duration.ZERO -> ""
            duration == Duration.ofSeconds(1) -> oneSecond
            else -> "${duration.seconds} $seconds"
        }
    }

    private fun formatMinutesAndSeconds(duration: Duration): String {
        val remainingSeconds = Duration.ofSeconds(duration.seconds % 60)
        return applyAnd(formatMinutes(duration), formatSeconds(remainingSeconds))
    }

    private fun formatHoursAndMinutes(duration: Duration): String {
        val remainingMinutes = Duration.ofMinutes(duration.toMinutes() % 60)
        return applyAnd(formatHours(duration), formatMinutes(remainingMinutes))
    }

    private fun formatDaysAndHours(duration: Duration): String {
        val remainingHours = Duration.ofHours(duration.toHours() % 24)
        return applyAnd(formatDays(duration), formatHours(remainingHours))
    }

    private fun formatWeeksAndDays(duration: Duration): String {
        val remainingDays = Duration.ofDays(duration.toDays() % 7)
        return applyAnd(formatWeeks(duration), formatDays(remainingDays))
    }

    private fun formatMinutes(duration: Duration): String {
        val durationInMinutes = duration.toMinutes()
        return when {
            durationInMinutes < 1L -> ""
            durationInMinutes == 1L -> oneMinute
            else -> "$durationInMinutes $minutes"
        }
    }

    private fun formatHours(duration: Duration): String {
        val durationInHours = duration.toHours()
        return when {
            durationInHours < 1L -> ""
            durationInHours == 1L -> oneHour
            else -> "$durationInHours $hours"
        }
    }

    private fun formatDays(duration: Duration): String {
        val durationInDays = duration.toDays()
        return when {
            durationInDays < 1L -> ""
            durationInDays == 1L -> oneDay
            else -> "$durationInDays $days"
        }
    }

    private fun formatWeeks(duration: Duration): String {
        val durationInWeeks = duration.toDays() / 7
        return when {
            durationInWeeks < 1L -> ""
            durationInWeeks == 1L -> oneWeek
            else -> "$durationInWeeks $weeks"
        }
    }

    private fun applyAnd(first: String, second: String): String {
        return if (second.isNotEmpty()) "$first $conjunction $second" else first
    }

}
