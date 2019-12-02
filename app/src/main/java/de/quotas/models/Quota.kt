package de.quotas.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import de.quotas.models.time.TimePeriod
import de.quotas.persistency.PeriodConverter
import de.quotas.persistency.ZonedDateTimeConverter
import org.threeten.bp.ZonedDateTime

@Entity
@TypeConverters(PeriodConverter::class, ZonedDateTimeConverter::class)
class Quota(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val period: TimePeriod,
    val startTime: ZonedDateTime,
    val lastFulfillmentTime: ZonedDateTime
) {

    fun isFulfilled(): Boolean {
        return period.currentIntervalIncludes(lastFulfillmentTime)
    }

}