package de.quotas.models.time

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.ZonedDateTime

class WeeklyNextIntervalTest {

    @Test
    fun startOfNextIntervalIsStartOfNextDay() {
        val representativeOfInterval = ZonedDateTime.parse("2020-01-25T21:40:01.614+01:00[Europe/Berlin]")
        val expectedStartOfNextInterval = ZonedDateTime.parse("2020-01-27T00:00:00+01:00[Europe/Berlin]")
        val nextInterval = Weekly().intervalIncluding(representativeOfInterval).next()
        assertThat(nextInterval.start()).isEqualTo(expectedStartOfNextInterval)
    }

}