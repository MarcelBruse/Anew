package de.quotas.models

import de.quotas.models.time.Daily
import de.quotas.models.time.Weekly
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.ZonedDateTime

class QuotaDueDateTest {

    @Test
    fun dueDateOfYetUnfulfilledDailyQuota() {
        val startTime = ZonedDateTime.parse("2020-01-27T17:30:00+01:00[Europe/Berlin]")
        val expectedDueDate = ZonedDateTime.parse("2020-01-28T00:00:00+01:00[Europe/Berlin]")
        val quota = Quota(0, "", Daily(), startTime, null)
        assertThat(quota.dueDate()).isEqualTo(expectedDueDate)
    }

    @Test
    fun dueDateOfYetUnfulfilledWeeklyQuota() {
        val startTime = ZonedDateTime.parse("2020-01-27T17:30:00+01:00[Europe/Berlin]")
        val expectedDueDate = ZonedDateTime.parse("2020-02-03T00:00:00+01:00[Europe/Berlin]")
        val quota = Quota(0, "", Weekly(), startTime, null)
        assertThat(quota.dueDate()).isEqualTo(expectedDueDate)
    }

    @Test
    fun dueDateOfFulfilledDailyQuota() {
        val startTime = ZonedDateTime.parse("2020-01-01T00:00:00+01:00[Europe/Berlin]")
        val lastFulfillmentTime = ZonedDateTime.parse("2020-01-22T17:30:00+01:00[Europe/Berlin]")
        val expectedDueDate = ZonedDateTime.parse("2020-01-24T00:00:00+01:00[Europe/Berlin]")
        val quota = Quota(0, "", Daily(), startTime, lastFulfillmentTime)
        assertThat(quota.dueDate()).isEqualTo(expectedDueDate)
    }


    @Test
    fun dueDateOfFulfilledWeeklyQuota() {
        val startTime = ZonedDateTime.parse("2020-01-01T00:00:00+01:00[Europe/Berlin]")
        val lastFulfillmentTime = ZonedDateTime.parse("2020-01-22T17:30:00+01:00[Europe/Berlin]")
        val expectedDueDate = ZonedDateTime.parse("2020-02-03T00:00:00+01:00[Europe/Berlin]")
        val quota = Quota(0, "", Weekly(), startTime, lastFulfillmentTime)
        assertThat(quota.dueDate()).isEqualTo(expectedDueDate)
    }

}