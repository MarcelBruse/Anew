package de.quotas.models

import de.quotas.models.time.Weekly
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.Duration
import org.threeten.bp.ZonedDateTime

class WeeklyPeriodTest {

    @Test
    fun oneWeekEqualsSevenDays() {
        val week = Weekly.getCurrentInterval().getDuration()
        val sevenDays = Duration.ofDays(7)
        assertThat(week).isEqualTo(sevenDays)
    }

    @Test
    fun oneNanoSecondBeforeMondayThisWeekIsNotIncluded() {
        val representative = ZonedDateTime.parse("2019-11-23T22:24:12.658+01:00[Europe/Berlin]")
        val testee = ZonedDateTime.parse("2019-11-17T23:59:59.999+01:00[Europe/Berlin]")
        assertIsNotIncludedInInterval(representative, testee)
    }

    @Test
    fun startOfMondayOfThisWeekIsIncluded() {
        val representative = ZonedDateTime.parse("2019-11-23T22:24:12.658+01:00[Europe/Berlin]")
        val testee = ZonedDateTime.parse("2019-11-18T00:00:00.000+01:00[Europe/Berlin]")
        assertIsIncludedInInterval(representative, testee)
    }

    @Test
    fun firstSecondOfMondayThisWeekIsIncluded() {
        val representative = ZonedDateTime.parse("2019-11-23T22:24:12.658+01:00[Europe/Berlin]")
        val testee = ZonedDateTime.parse("2019-11-18T00:00:01.000+01:00[Europe/Berlin]")
        assertIsIncludedInInterval(representative, testee)
    }

    @Test
    fun wednesdeyIsIncluded() {
        val representative = ZonedDateTime.parse("2019-11-23T22:24:12.658+01:00[Europe/Berlin]")
        val testee = ZonedDateTime.parse("2019-11-20T10:05:12.658+01:00[Europe/Berlin]")
        assertIsIncludedInInterval(representative, testee)
    }

    @Test
    fun oneNanoSecondBeforeNextWeekIsIncluded() {
        val representative = ZonedDateTime.parse("2019-11-23T22:24:12.658+01:00[Europe/Berlin]")
        val testee = ZonedDateTime.parse("2019-11-24T23:59:59.999+01:00[Europe/Berlin]")
        assertIsIncludedInInterval(representative, testee)
    }

    @Test
    fun startOfMondayNextWeekIsNotIncluded() {
        val representative = ZonedDateTime.parse("2019-11-23T22:24:12.658+01:00[Europe/Berlin]")
        val testee = ZonedDateTime.parse("2019-11-25T00:00:00.000+01:00[Europe/Berlin]")
        assertIsNotIncludedInInterval(representative, testee)
    }

    @Test
    fun firstNanoSecondOfMondayNextWeekIsNotIncluded() {
        val representative = ZonedDateTime.parse("2019-11-23T22:24:12.658+01:00[Europe/Berlin]")
        val testee = ZonedDateTime.parse("2019-11-25T00:00:00.001+01:00[Europe/Berlin]")
        assertIsNotIncludedInInterval(representative, testee)
    }

    private fun assertIsIncludedInInterval(representative: ZonedDateTime, queryZonedDateTime: ZonedDateTime) {
        assertThat(includesZonedDateTime(representative, queryZonedDateTime)).isTrue()
    }

    private fun assertIsNotIncludedInInterval(representative: ZonedDateTime, queryZonedDateTime: ZonedDateTime) {
        assertThat(includesZonedDateTime(representative, queryZonedDateTime)).isFalse()
    }

    private fun includesZonedDateTime(representative: ZonedDateTime, queryZonedDateTime: ZonedDateTime): Boolean {
        return Weekly.getIntervalFromRepresentative(representative).includesInstant(queryZonedDateTime)
    }

}