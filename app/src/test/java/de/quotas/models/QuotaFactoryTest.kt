package de.quotas.models

import de.quotas.models.time.Daily
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.ZonedDateTime

class QuotaFactoryTest {

    @Test
    fun newQuota() {
        val quota = QuotaFactory.newQuota()
        assertThat(quota.id).isEqualTo(0)
        assertThat(quota.name).isEqualTo("")
        assertThat(quota.period).isInstanceOf(Daily::class.java)
        assertThat(quota.startTime).isInstanceOf(ZonedDateTime::class.java)
        assertThat(quota.lastFulfillmentTime).isNull()
    }

}