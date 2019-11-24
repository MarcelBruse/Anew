package de.quotas.models

import de.quotas.models.time.Daily
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.Duration
import org.threeten.bp.ZonedDateTime

class DailyPeriodTest {

    @Test
    fun oneDayEqualsTwentyFourHours() {
        val day = Daily.getCurrentInterval().getDuration()
        val twentyFourHours = Duration.ofHours(24)
        assertThat(day).isEqualTo(twentyFourHours)
    }

    @Test
    fun oneSecondBeforeTodayIsNotIncluded() {
        val representative = ZonedDateTime.parse("2019-11-16T21:34:24.621+01:00[Europe/Berlin]")
        val testee = ZonedDateTime.parse("2019-11-15T23:59:59.999+01:00[Europe/Berlin]")
        assertIsNotIncludedInInterval(representative, testee)
    }

    @Test
    fun midnightBetweenYesterdayAndTodayIsIncluded() {
        val representative = ZonedDateTime.parse("2019-11-16T21:34:24.621+01:00[Europe/Berlin]")
        val testee = ZonedDateTime.parse("2019-11-16T00:00:00.000+01:00[Europe/Berlin]")
        assertIsIncludedInInterval(representative, testee)
    }

    @Test
    fun oneSecondAfterMidnightBetweenYesterdayAndTodayIsIncluded() {
        val representative = ZonedDateTime.parse("2019-11-16T21:34:24.621+01:00[Europe/Berlin]")
        val testee = ZonedDateTime.parse("2019-11-16T00:00:01.000+01:00[Europe/Berlin]")
        assertIsIncludedInInterval(representative, testee)
    }

    @Test
    fun tenOClockIsIncluded() {
        val representative = ZonedDateTime.parse("2019-11-16T21:34:24.621+01:00[Europe/Berlin]")
        val testee = ZonedDateTime.parse("2019-11-16T10:00:00.000+01:00[Europe/Berlin]")
        assertIsIncludedInInterval(representative, testee)
    }

    @Test
    fun oneSecondBeforeTomorrowIsIncluded() {
        val representative = ZonedDateTime.parse("2019-11-16T21:34:24.621+01:00[Europe/Berlin]")
        val testee = ZonedDateTime.parse("2019-11-16T23:59:59.999+01:00[Europe/Berlin]")
        assertIsIncludedInInterval(representative, testee)
    }

    @Test
    fun theSecondAfterMidnightNextDayIsNotIncluded() {
        val representative = ZonedDateTime.parse("2019-11-16T21:34:24.621+01:00[Europe/Berlin]")
        val testee = ZonedDateTime.parse("2019-11-17T00:00:00.000+01:00[Europe/Berlin]")
        assertIsNotIncludedInInterval(representative, testee)
    }

    private fun assertIsIncludedInInterval(representative: ZonedDateTime, queryInstant: ZonedDateTime) {
        assertThat(includesInstant(representative, queryInstant)).isTrue()
    }

    private fun assertIsNotIncludedInInterval(representative: ZonedDateTime, queryInstant: ZonedDateTime) {
        assertThat(includesInstant(representative, queryInstant)).isFalse()
    }

    private fun includesInstant(representative: ZonedDateTime, queryInstant: ZonedDateTime): Boolean {
        return Daily.getIntervalFromRepresentative(representative).includesInstant(queryInstant)
    }

}