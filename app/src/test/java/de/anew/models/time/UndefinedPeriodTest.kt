package de.anew.models.time

import org.junit.Test
import org.threeten.bp.ZonedDateTime

class UndefinedPeriodTest {

    @Test(expected = UnsupportedOperationException::class)
    fun clockIsUnsupported() {
        UndefinedPeriod.clock()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun getCurrentIntervalIsUnsupported() {
        UndefinedPeriod.currentInterval()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun getIntervalContainingIsUnsupported() {
        UndefinedPeriod.intervalIncluding(ZonedDateTime.now())
    }

    @Test(expected = UnsupportedOperationException::class)
    fun currentIntervalIncludesIsUnsupported() {
        UndefinedPeriod.currentIntervalIncludes(ZonedDateTime.now())
    }

}