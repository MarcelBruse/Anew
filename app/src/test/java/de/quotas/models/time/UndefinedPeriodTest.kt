package de.quotas.models.time

import org.junit.Test
import org.threeten.bp.ZonedDateTime

class UndefinedPeriodTest {

    @Test(expected = UnsupportedOperationException::class)
    fun getIntervalContainingIsUnsupported() {
        UndefinedPeriod.getIntervalContaining(ZonedDateTime.now())
    }

    @Test(expected = UnsupportedOperationException::class)
    fun currentIntervalIncludesIsUnsupported() {
        UndefinedPeriod.currentIntervalIncludes(ZonedDateTime.now())
    }

}