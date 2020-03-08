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
            absoluteDuration < Duration.ofMinutes(1) -> formatSeconds(absoluteDuration)
            absoluteDuration < Duration.ofHours(1) -> formatMinutesAndSeconds(absoluteDuration)
            absoluteDuration < Duration.ofDays(1) -> formatHoursAndMinutes(absoluteDuration)
            absoluteDuration < Duration.ofDays(7) -> formatDaysAndHours(absoluteDuration)
            else -> formatWeeksAndDays(absoluteDuration)
        }
    }

    private fun formatSeconds(duration: Duration): String {
        return when {
            duration <= Duration.ZERO -> "${duration.seconds} $seconds"
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
            durationInMinutes < 1L -> "$durationInMinutes $minutes"
            durationInMinutes == 1L -> oneMinute
            else -> "$durationInMinutes $minutes"
        }
    }

    private fun formatHours(duration: Duration): String {
        val durationInHours = duration.toHours()
        return when {
            durationInHours < 1L -> "$durationInHours $hours"
            durationInHours == 1L -> oneHour
            else -> "$durationInHours $hours"
        }
    }

    private fun formatDays(duration: Duration): String {
        val durationInDays = duration.toDays()
        return when {
            durationInDays < 1L -> "$durationInDays $days"
            durationInDays == 1L -> oneDay
            else -> "$durationInDays $days"
        }
    }

    private fun formatWeeks(duration: Duration): String {
        val durationInWeeks = duration.toDays() / 7
        return when {
            durationInWeeks < 1L -> "0 $weeks"
            durationInWeeks == 1L -> oneWeek
            else -> "$durationInWeeks $weeks"
        }
    }

    private fun applyAnd(first: String, second: String) = "$first $conjunction $second"

}
