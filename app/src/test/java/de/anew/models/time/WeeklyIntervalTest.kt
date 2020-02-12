package de.anew.models.time

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.Clock
import org.threeten.bp.ZonedDateTime

class WeeklyIntervalTest {

    @Test
    fun clockIsInitialized() {
        val systemClock = Clock.systemUTC()
        val weekly = Weekly(systemClock)
        assertThat(systemClock).isNotNull
        assertThat(weekly.clock()).isEqualTo(systemClock)
    }

    @Test
    fun startOfIntervalIsMonday() {
        val representativeOfInterval = ZonedDateTime.parse("2019-11-16T10:34:24+01:00[Europe/Berlin]")
        val monday = ZonedDateTime.parse("2019-11-11T00:00:00+01:00[Europe/Berlin]")
        val week = Weekly().intervalIncluding(representativeOfInterval)
        assertThat(week.startsAt()).isEqualTo(monday)
    }

    @Test
    fun startOfNextIntervalIsStartOfNextWeek() {
        val representativeOfInterval = ZonedDateTime.parse("2020-01-25T21:40:01.614+01:00[Europe/Berlin]")
        val expectedStartOfNextInterval = ZonedDateTime.parse("2020-01-27T00:00:00+01:00[Europe/Berlin]")
        val nextWeek = Weekly().intervalIncluding(representativeOfInterval).next()
        assertThat(nextWeek.startsAt()).isEqualTo(expectedStartOfNextInterval)
    }

    @Test
    fun endOfWeekEqualsStartsOfNextWeek() {
        val representativeOfInterval = ZonedDateTime.parse("2019-11-15T19:00:00+01:00[Europe/Berlin]")
        val week = Weekly().intervalIncluding(representativeOfInterval)
        val nextMonday = ZonedDateTime.parse("2019-11-18T00:00:00+01:00[Europe/Berlin]")
        assertThat(week.endsBefore()).isEqualTo(nextMonday)
        assertThat(week.endsBefore()).isEqualTo(week.next().startsAt())
    }

}