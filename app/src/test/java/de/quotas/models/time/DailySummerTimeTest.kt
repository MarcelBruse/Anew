package de.quotas.models.time

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.ZonedDateTime

class DailySummerTimeTest {

    @Test
    fun endOfDayBeforeLastWinterTimeDayIsNotIncluded() {
        val lastWinterTimeDayEnd = ZonedDateTime.parse("2020-03-28T23:59:59+01:00[Europe/Berlin]")

        val winterRepresentative = ZonedDateTime.parse("2020-03-29T01:00+01:00[Europe/Berlin]")
        val dayFromWinterRepresentative = Daily().intervalIncluding(winterRepresentative)
        assertThat(dayFromWinterRepresentative.includes(lastWinterTimeDayEnd)).isFalse()

        val summerRepresentative = ZonedDateTime.parse("2020-03-29T03:00+02:00[Europe/Berlin]")
        val dayFromSummerRepresentative = Daily().intervalIncluding(summerRepresentative)
        assertThat(dayFromSummerRepresentative.includes(lastWinterTimeDayEnd)).isFalse()
    }

    @Test
    fun startOfLastWinterTimeDayIsIncluded() {
        val lastWinterTimeDayStart = ZonedDateTime.parse("2020-03-29T00:00+01:00[Europe/Berlin]")

        val winterRepresentative = ZonedDateTime.parse("2020-03-29T01:59+01:00[Europe/Berlin]")
        val dayFromWinterRepresentative = Daily().intervalIncluding(winterRepresentative)
        assertThat(dayFromWinterRepresentative.includes(lastWinterTimeDayStart)).isTrue()

        val summerRepresentative = ZonedDateTime.parse("2020-03-29T23:59+02:00[Europe/Berlin]")
        val dayFromSummerRepresentative = Daily().intervalIncluding(summerRepresentative)
        assertThat(dayFromSummerRepresentative.includes(lastWinterTimeDayStart)).isTrue()
    }

    @Test
    fun endOfFirstSummerTimeDayIsIncluded() {
        val endOfFirstSummerTimeDay = ZonedDateTime.parse("2020-03-29T23:59:59+02:00[Europe/Berlin]")

        val winterRepresentative = ZonedDateTime.parse("2020-03-29T01:59+01:00[Europe/Berlin]")
        val dayFromWinterRepresentative = Daily().intervalIncluding(winterRepresentative)
        assertThat(dayFromWinterRepresentative.includes(endOfFirstSummerTimeDay)).isTrue()

        val summerRepresentative = ZonedDateTime.parse("2020-03-29T03:00+02:00[Europe/Berlin]")
        val dayFromSummerRepresentative = Daily().intervalIncluding(summerRepresentative)
        assertThat(dayFromSummerRepresentative.includes(endOfFirstSummerTimeDay)).isTrue()
    }

    @Test
    fun startOfDayAfterFirstSummerTimeDayIsNotIncluded() {
        val startOfDayAfterFirstSummerTimeDay = ZonedDateTime.parse("2020-03-30T00:00+02:00[Europe/Berlin]")

        val winterRepresentative = ZonedDateTime.parse("2020-03-29T01:59+01:00[Europe/Berlin]")
        val dayFromWinterRepresentative = Daily().intervalIncluding(winterRepresentative)
        assertThat(dayFromWinterRepresentative.includes(startOfDayAfterFirstSummerTimeDay)).isFalse()

        val summerRepresentative = ZonedDateTime.parse("2020-03-29T03:00+02:00[Europe/Berlin]")
        val dayFromSummerRepresentative = Daily().intervalIncluding(summerRepresentative)
        assertThat(dayFromSummerRepresentative.includes(startOfDayAfterFirstSummerTimeDay)).isFalse()
    }

    @Test
    fun nextIntervalStartsInSummerTime() {
        val lastWinterTimeDayStart = ZonedDateTime.parse("2020-03-29T00:00+01:00[Europe/Berlin]")
        val firstSummerTimeDayStart = ZonedDateTime.parse("2020-03-30T00:00+02:00[Europe/Berlin]")
        val winterTimeDay = Daily().intervalIncluding(lastWinterTimeDayStart)
        assertThat(winterTimeDay.next().start()).isEqualTo(firstSummerTimeDayStart)
    }

}