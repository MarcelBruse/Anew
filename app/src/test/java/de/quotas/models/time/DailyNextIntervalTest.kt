package de.quotas.models.time

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.ZonedDateTime

class DailyNextIntervalTest {

    @Test
    fun startOfNextIntervalIsStartOfNextDay() {
        val representativeOfInterval = ZonedDateTime.parse("2019-11-16T10:34:24.621+01:00[Europe/Berlin]")
        val expectedStartOfNextInterval = ZonedDateTime.parse("2019-11-17T00:00:00.000+01:00[Europe/Berlin]")
        val nextInterval = Daily().intervalIncluding(representativeOfInterval).next()
        assertThat(nextInterval.start()).isEqualTo(expectedStartOfNextInterval)
    }

}