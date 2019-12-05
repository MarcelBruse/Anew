package de.quotas.models.time

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.Clock
import org.threeten.bp.ZonedDateTime

class WeeklyPeriodTest {

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

    @Test
    fun twentyEighthOfFebruaryAndTwentyNinthOfFebruary() {
        val representative = ZonedDateTime.parse("2020-02-29T23:59:59.000+01:00[Europe/Berlin]")
        val testee = ZonedDateTime.parse("2020-02-28T23:59:59.000+01:00[Europe/Berlin]")
        assertIsIncludedInInterval(representative, testee)
    }

    @Test
    fun twentyNinthOfFebruaryAndTwentyEighthOfFeburary() {
        val representative = ZonedDateTime.parse("2020-02-28T23:59:59.000+01:00[Europe/Berlin]")
        val testee = ZonedDateTime.parse("2020-02-29T23:59:59.000+01:00[Europe/Berlin]")
        assertIsIncludedInInterval(representative, testee)
    }

    @Test
    fun twentyNinthOfFebruaryAndFirstOfMarch() {
        val twentyNinthOfFebruary = ZonedDateTime.parse("2020-02-29T23:59:59.000+01:00[Europe/Berlin]")
        val firstOfMarch = ZonedDateTime.parse("2020-03-01T02:00:00.000+01:00[Europe/Berlin]")
        assertIsIncludedInInterval(twentyNinthOfFebruary, firstOfMarch)
    }

    @Test
    fun nowIsAlwaysIncludedInCurrentInterval() {
        Weekly(Clock.systemDefaultZone()).currentIntervalIncludes(ZonedDateTime.now())
    }

    private fun assertIsIncludedInInterval(representative: ZonedDateTime, queryZonedDateTime: ZonedDateTime) {
        assertThat(includesZonedDateTime(representative, queryZonedDateTime)).isTrue()
    }

    private fun assertIsNotIncludedInInterval(representative: ZonedDateTime, queryZonedDateTime: ZonedDateTime) {
        assertThat(includesZonedDateTime(representative, queryZonedDateTime)).isFalse()
    }

    private fun includesZonedDateTime(representative: ZonedDateTime, queryZonedDateTime: ZonedDateTime): Boolean {
        return Weekly(Clock.systemDefaultZone()).getIntervalIncluding(representative).includes(queryZonedDateTime)
    }

}