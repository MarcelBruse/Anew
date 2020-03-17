package de.anew.activities.tasks

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.Duration
import java.util.*

@RunWith(AndroidJUnit4::class)
class DurationFormatterTest: LocalizationTest() {

    @Test
    fun timeIsUp() {
        val duration = Duration.ZERO
        checkFormatOf(duration, "0 seconds", enEN)
        checkFormatOf(duration, "0 Sekunden", deDE)
    }

    @Test
    fun oneSecond() {
        val duration = Duration.ofSeconds(1)
        checkFormatOf(duration, "1 second", enEN)
        checkFormatOf(duration, "1 Sekunde", deDE)
    }

    @Test
    fun twoSeconds() {
        val duration = Duration.ofSeconds(2)
        checkFormatOf(duration, "2 seconds", enEN)
        checkFormatOf(duration, "2 Sekunden", deDE)
    }

    @Test
    fun fiftyNineSeconds() {
        val duration = Duration.ofSeconds(59)
        checkFormatOf(duration, "59 seconds", enEN)
        checkFormatOf(duration, "59 Sekunden", deDE)
    }

    @Test
    fun oneMinute() {
        val duration = Duration.ofSeconds(60)
        checkFormatOf(duration, "1 minute", enEN)
        checkFormatOf(duration, "1 Minute", deDE)
    }

    @Test
    fun sixtyOneSeconds() {
        val duration = Duration.ofSeconds(61)
        checkFormatOf(duration, "1 minute and 1 second", enEN)
        checkFormatOf(duration, "1 Minute und 1 Sekunde", deDE)
    }

    @Test
    fun sixtyTwoSeconds() {
        val duration = Duration.ofSeconds(62)
        checkFormatOf(duration, "1 minute and 2 seconds", enEN)
        checkFormatOf(duration, "1 Minute und 2 Sekunden", deDE)
    }

    @Test
    fun oneMinuteAndFiftyNineSeconds() {
        val duration = Duration.ofMinutes(1).plusSeconds(59)
        checkFormatOf(duration, "1 minute and 59 seconds", enEN)
        checkFormatOf(duration, "1 Minute und 59 Sekunden", deDE)
    }

    @Test
    fun twoMinutes() {
        val duration = Duration.ofMinutes(2)
        checkFormatOf(duration, "2 minutes", enEN)
        checkFormatOf(duration, "2 Minuten", deDE)
    }

    @Test
    fun twoMinutesAndOneSecond() {
        val duration = Duration.ofMinutes(2).plusSeconds(1)
        checkFormatOf(duration, "2 minutes and 1 second", enEN)
        checkFormatOf(duration, "2 Minuten und 1 Sekunde", deDE)
    }

    @Test
    fun twoMinutesAndThirtySeconds() {
        val duration = Duration.ofMinutes(2).plusSeconds(30)
        checkFormatOf(duration, "2 minutes and 30 seconds", enEN)
        checkFormatOf(duration, "2 Minuten und 30 Sekunden", deDE)
    }

    @Test
    fun fiftyNineMinutesAndFiftyNineSeconds() {
        val duration = Duration.ofMinutes(59).plusSeconds(59)
        checkFormatOf(duration, "59 minutes and 59 seconds", enEN)
        checkFormatOf(duration, "59 Minuten und 59 Sekunden", deDE)
    }

    @Test
    fun oneHour() {
        val duration = Duration.ofHours(1)
        checkFormatOf(duration, "1 hour", enEN)
        checkFormatOf(duration, "1 Stunde", deDE)
    }

    @Test
    fun oneHourAndOneSecond() {
        val duration = Duration.ofHours(1).plusSeconds(1)
        checkFormatOf(duration, "1 hour", enEN)
        checkFormatOf(duration, "1 Stunde", deDE)
    }

    @Test
    fun oneHourAndOneMinute() {
        val duration = Duration.ofHours(1).plusMinutes(1)
        checkFormatOf(duration, "1 hour and 1 minute", enEN)
        checkFormatOf(duration, "1 Stunde und 1 Minute", deDE)
    }

    @Test
    fun twoHoursAndThirtyMinutes() {
        val duration = Duration.ofHours(2).plusMinutes(30)
        checkFormatOf(duration, "2 hours and 30 minutes", enEN)
        checkFormatOf(duration, "2 Stunden und 30 Minuten", deDE)
    }

    @Test
    fun twentyThreeHoursAndFiftyNineMinutes() {
        val duration = Duration.ofHours(23).plusMinutes(59)
        checkFormatOf(duration, "23 hours and 59 minutes", enEN)
        checkFormatOf(duration, "23 Stunden und 59 Minuten", deDE)
    }

    @Test
    fun oneDay() {
        val duration = Duration.ofDays(1)
        checkFormatOf(duration, "1 day", enEN)
        checkFormatOf(duration, "1 Tag", deDE)
    }

    @Test
    fun oneDayAndOneMinute() {
        val duration = Duration.ofDays(1).plusMinutes(1)
        checkFormatOf(duration, "1 day", enEN)
        checkFormatOf(duration, "1 Tag", deDE)
    }

    @Test
    fun oneDayAndOneHour() {
        val duration = Duration.ofDays(1).plusHours(1)
        checkFormatOf(duration, "1 day and 1 hour", enEN)
        checkFormatOf(duration, "1 Tag und 1 Stunde", deDE)
    }

    @Test
    fun oneDayAndTwoHours() {
        val duration = Duration.ofDays(1).plusHours(2)
        checkFormatOf(duration, "1 day and 2 hours", enEN)
        checkFormatOf(duration, "1 Tag und 2 Stunden", deDE)
    }

    @Test
    fun oneDayAndTwentyThreeHours() {
        val duration = Duration.ofDays(1).plusHours(23)
        checkFormatOf(duration, "1 day and 23 hours", enEN)
        checkFormatOf(duration, "1 Tag und 23 Stunden", deDE)
    }

    @Test
    fun twoDays() {
        val duration = Duration.ofDays(2)
        checkFormatOf(duration, "2 days", enEN)
        checkFormatOf(duration, "2 Tagen", deDE)
    }

    @Test
    fun twoDaysAndTwelveHours() {
        val duration = Duration.ofDays(2).plusHours(12)
        checkFormatOf(duration, "2 days and 12 hours", enEN)
        checkFormatOf(duration, "2 Tagen und 12 Stunden", deDE)
    }

    @Test
    fun sixDaysAndTwentyThreeHours() {
        val duration = Duration.ofDays(6).plusHours(23)
        checkFormatOf(duration, "6 days and 23 hours", enEN)
        checkFormatOf(duration, "6 Tagen und 23 Stunden", deDE)
    }

    @Test
    fun oneWeek() {
        val duration = Duration.ofDays(7)
        checkFormatOf(duration, "1 week", enEN)
        checkFormatOf(duration, "1 Woche", deDE)
    }

    @Test
    fun oneWeekAndOneHour() {
        val duration = Duration.ofDays(7).plusHours(1)
        checkFormatOf(duration, "1 week", enEN)
        checkFormatOf(duration, "1 Woche", deDE)
    }

    @Test
    fun oneWeekAndOneDay() {
        val duration = Duration.ofDays(7).plusDays(1)
        checkFormatOf(duration, "1 week and 1 day", enEN)
        checkFormatOf(duration, "1 Woche und 1 Tag", deDE)
    }

    @Test
    fun oneWeekAndTwoDays() {
        val duration = Duration.ofDays(7).plusDays(2)
        checkFormatOf(duration, "1 week and 2 days", enEN)
        checkFormatOf(duration, "1 Woche und 2 Tagen", deDE)
    }

    @Test
    fun oneWeekAndSixDays() {
        val duration = Duration.ofDays(7).plusDays(6)
        checkFormatOf(duration, "1 week and 6 days", enEN)
        checkFormatOf(duration, "1 Woche und 6 Tagen", deDE)
    }

    @Test
    fun twoWeeks() {
        val duration = Duration.ofDays(14)
        checkFormatOf(duration, "2 weeks", enEN)
        checkFormatOf(duration, "2 Wochen", deDE)
    }

    @Test
    fun twoWeeksAndThreeDays() {
        val duration = Duration.ofDays(17)
        checkFormatOf(duration, "2 weeks and 3 days", enEN)
        checkFormatOf(duration, "2 Wochen und 3 Tagen", deDE)
    }

    @Test
    fun minusOneSecond() {
        val duration = Duration.ofSeconds(1).negated()
        assertThat(duration.isNegative)
        checkFormatOf(duration, "1 second", enEN)
        checkFormatOf(duration, "1 Sekunde", deDE)
    }

    @Test
    fun minusOneMinuteAndTwelveSeconds() {
        val duration = Duration.ofSeconds(72).negated()
        assertThat(duration.isNegative)
        checkFormatOf(duration, "1 minute and 12 seconds", enEN)
        checkFormatOf(duration, "1 Minute und 12 Sekunden", deDE)
    }

    @Test
    fun minusThreeDaysAndOneHour() {
        val duration = Duration.ofDays(3).plusHours(1).negated()
        assertThat(duration.isNegative)
        checkFormatOf(duration, "3 days and 1 hour", enEN)
        checkFormatOf(duration, "3 Tagen und 1 Stunde", deDE)
    }

    private fun checkFormatOf(duration: Duration, expected: String, locale: Locale) {
        val context = createNewContextWithLocale(locale)
        assertThat(context).isNotNull
        context?.let {
            assertThat(DurationFormatter(it).format(duration)).isEqualTo(expected)
        }
    }

}
