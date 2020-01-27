package de.quotas.models.time

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.Clock
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

class DailyPeriodTest {

    @Test
    fun oneSecondBeforeTodayIsNotIncluded() {
        val representative = ZonedDateTime.parse("2019-11-16T10:34:24.621+01:00[Europe/Berlin]")
        val testee = ZonedDateTime.parse("2019-11-15T23:59:59.999+01:00[Europe/Berlin]")
        assertIsNotIncludedInInterval(representative, testee)
    }

    @Test
    fun midnightBetweenYesterdayAndTodayIsIncluded() {
        val representative = ZonedDateTime.parse("2019-11-16T21:30:24.000+01:00[Europe/Berlin]")
        val testee = ZonedDateTime.parse("2019-11-16T00:00:00.000+01:00[Europe/Berlin]")
        assertIsIncludedInInterval(representative, testee)
    }

    @Test
    fun oneSecondAfterMidnightBetweenYesterdayAndTodayIsIncluded() {
        val representative = ZonedDateTime.parse("2019-11-16T21:34:00.621+01:00[Europe/Berlin]")
        val testee = ZonedDateTime.parse("2019-11-16T00:00:01.000+01:00[Europe/Berlin]")
        assertIsIncludedInInterval(representative, testee)
    }

    @Test
    fun tenOClockIsIncluded() {
        val representative = ZonedDateTime.parse("2019-11-16T21:10:24.621+01:00[Europe/Berlin]")
        val testee = ZonedDateTime.parse("2019-11-16T10:00:00.000+01:00[Europe/Berlin]")
        assertIsIncludedInInterval(representative, testee)
    }

    @Test
    fun oneSecondBeforeTomorrowIsIncluded() {
        val representative = ZonedDateTime.parse("2019-11-16T01:00:24.621+01:00[Europe/Berlin]")
        val testee = ZonedDateTime.parse("2019-11-16T23:59:59.999+01:00[Europe/Berlin]")
        assertIsIncludedInInterval(representative, testee)
    }

    @Test
    fun theSecondAfterMidnightNextDayIsNotIncluded() {
        val representative = ZonedDateTime.parse("2019-11-16T02:34:24.621+01:00[Europe/Berlin]")
        val testee = ZonedDateTime.parse("2019-11-17T00:00:00.000+01:00[Europe/Berlin]")
        assertIsNotIncludedInInterval(representative, testee)
    }

    @Test
    fun twentyNinthOfFebruaryInLeapYear() {
        val representative = ZonedDateTime.parse("2020-02-29T01:00:00.000+01:00[Europe/Berlin]")
        val testee = ZonedDateTime.parse("2020-02-29T02:00:00.000+01:00[Europe/Berlin]")
        assertIsIncludedInInterval(representative, testee)
    }

    @Test
    fun twentyNinthOfFebruaryToFirstOfMarch() {
        val representative = ZonedDateTime.parse("2020-02-29T23:59:59.000+01:00[Europe/Berlin]")
        val testee = ZonedDateTime.parse("2020-03-01T02:00:00.000+01:00[Europe/Berlin]")
        assertIsNotIncludedInInterval(representative, testee)
    }

    @Test
    fun nowIsAlwaysIncludedInCurrentInterval() {
        Daily(Clock.systemDefaultZone()).currentIntervalIncludes(ZonedDateTime.now())
    }

    private fun assertIsIncludedInInterval(representative: ZonedDateTime, queryInstant: ZonedDateTime) {
        assertThat(includesInstant(representative, queryInstant)).isTrue()
    }

    private fun assertIsNotIncludedInInterval(representative: ZonedDateTime, queryInstant: ZonedDateTime) {
        assertThat(includesInstant(representative, queryInstant)).isFalse()
    }

    private fun includesInstant(representative: ZonedDateTime, queryInstant: ZonedDateTime): Boolean {
        return Daily(Clock.systemDefaultZone()).intervalIncluding(representative).includes(queryInstant)
    }

    @Test
    fun currentIntervalShortCut() {
        val expectedStartOfInterval = ZonedDateTime.parse("2020-02-01T00:00:00.000+01:00[Europe/Berlin]")
        val now = ZonedDateTime.parse("2020-02-01T02:00:00.000+01:00[Europe/Berlin]")
        val instant = now.plusHours(1)
        val fixedClock = Clock.fixed(now.toInstant(), ZoneId.systemDefault())
        val intervalIncluding = Daily(fixedClock).intervalIncluding(instant)
        val currentInterval = Daily(fixedClock).currentInterval()
        assertThat(intervalIncluding.startsAt()).isEqualTo(currentInterval.startsAt())
        assertThat(intervalIncluding.startsAt()).isEqualTo(expectedStartOfInterval)
    }

    @Test
    fun dailyHashCode() {
        val hashCode = Daily(Clock.systemDefaultZone()).hashCode()
        assertThat(hashCode).isEqualTo(Daily::class.hashCode() + Clock.systemDefaultZone()::class.hashCode())
    }

    @Test
    fun dailyIsEqualToOtherDaily() {
        assertThat(Daily()).isEqualTo(Daily(Clock.systemDefaultZone()))
    }

    @Test
    fun dailyIsNotEqualToOtherDaily() {
        val fixedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault())
        val defaultClock = Clock.systemDefaultZone()
        assertThat(Daily(fixedClock)).isNotEqualTo(Daily(defaultClock))
    }

}