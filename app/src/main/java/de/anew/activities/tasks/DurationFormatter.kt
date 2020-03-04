package de.anew.activities.tasks

import android.content.Context
import de.anew.R
import org.threeten.bp.Duration

class DurationFormatter(context: Context) {

    private val dSeconds = context.getString(R.string.d_seconds)

    private val oneSecond = context.getString(R.string.one_second)

    private val dMinutes = context.getString(R.string.d_minutes)

    private val oneMinute = context.getString(R.string.one_minute)

    private val dHours = context.getString(R.string.d_hours)

    private val oneHour = context.getString(R.string.one_hour)

    private val dDays = context.getString(R.string.d_days)

    private val oneDay = context.getString(R.string.one_day)

    private val dWeeks = context.getString(R.string.d_weeks)

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
            duration <= Duration.ZERO -> dSeconds.format(duration.seconds)
            duration == Duration.ofSeconds(1) -> oneSecond
            else -> dSeconds.format(duration.seconds)
        }
    }

    private fun formatMinutesAndSeconds(duration: Duration): String {
        val remainingSeconds = Duration.ofSeconds(duration.seconds % 60)
        return formatMinutes(duration) + formatAnd() + formatSeconds(remainingSeconds)
    }

    private fun formatHoursAndMinutes(duration: Duration): String {
        val remainingMinutes = Duration.ofMinutes(duration.toMinutes() % 60)
        return formatHours(duration) + formatAnd() + formatMinutes(remainingMinutes)
    }

    private fun formatDaysAndHours(duration: Duration): String {
        val remainingHours = Duration.ofHours(duration.toHours() % 24)
        return formatDays(duration) + formatAnd() + formatHours(remainingHours)
    }

    private fun formatWeeksAndDays(duration: Duration): String {
        val remainingDays = Duration.ofDays(duration.toDays() % 7)
        return formatWeeks(duration) + formatAnd() + formatDays(remainingDays)
    }

    private fun formatMinutes(duration: Duration): String {
        val durationInMinutes = duration.toMinutes()
        return when {
            durationInMinutes < 1L -> dMinutes.format(durationInMinutes)
            durationInMinutes == 1L -> oneMinute
            else -> dMinutes.format(durationInMinutes)
        }
    }

    private fun formatHours(duration: Duration): String {
        val durationInHours = duration.toHours()
        return when {
            durationInHours < 1L -> dHours.format(durationInHours)
            durationInHours == 1L -> oneHour
            else -> dHours.format(durationInHours)
        }
    }

    private fun formatDays(duration: Duration): String {
        val durationInDays = duration.toDays()
        return when {
            durationInDays < 1L -> dDays.format(durationInDays)
            durationInDays == 1L -> oneDay
            else -> dDays.format(durationInDays)
        }
    }

    private fun formatWeeks(duration: Duration): String {
        val durationInWeeks = duration.toDays() / 7
        return when {
            durationInWeeks < 1L -> dWeeks.format(0)
            durationInWeeks == 1L -> oneWeek
            else -> dWeeks.format(durationInWeeks)
        }
    }

    private fun formatAnd() = " %s ".format(conjunction)

}
