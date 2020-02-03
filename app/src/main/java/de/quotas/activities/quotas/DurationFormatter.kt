package de.quotas.activities.quotas

import android.content.Context
import de.quotas.R
import org.threeten.bp.Duration

class DurationFormatter(private val context: Context) {

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
            duration <= Duration.ZERO -> context.getString(R.string.d_seconds).format(duration.seconds)
            duration == Duration.ofSeconds(1) -> context.getString(R.string.one_second)
            else -> context.getString(R.string.d_seconds).format(duration.seconds)
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
            durationInMinutes < 1L -> context.getString(R.string.d_minutes).format(durationInMinutes)
            durationInMinutes == 1L -> context.getString(R.string.one_minute)
            else -> context.getString(R.string.d_minutes).format(durationInMinutes)
        }
    }

    private fun formatHours(duration: Duration): String {
        val durationInHours = duration.toHours()
        return when {
            durationInHours < 1L -> context.getString(R.string.d_hours).format(durationInHours)
            durationInHours == 1L -> context.getString(R.string.one_hour)
            else -> context.getString(R.string.d_hours).format(durationInHours)
        }
    }

    private fun formatDays(duration: Duration): String {
        val durationInDays = duration.toDays()
        return when {
            durationInDays < 1L -> context.getString(R.string.d_days).format(durationInDays)
            durationInDays == 1L -> context.getString(R.string.one_day)
            else -> context.getString(R.string.d_days).format(durationInDays)
        }
    }

    private fun formatWeeks(duration: Duration): String {
        val durationInWeeks = duration.toDays() / 7
        return when {
            durationInWeeks < 1L -> context.getString(R.string.d_weeks).format(0)
            durationInWeeks == 1L -> context.getString(R.string.one_week)
            else -> context.getString(R.string.d_weeks).format(durationInWeeks)
        }
    }

    private fun formatAnd() = " %s ".format(context.getString(R.string.and))

}
