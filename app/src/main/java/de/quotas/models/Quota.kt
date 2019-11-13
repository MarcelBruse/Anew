package de.quotas.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity
@TypeConverters(PeriodConverter::class)
class Quota(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val period: Period,
    val startTime: Long,
    val lastFulfillmentTime: Long
) {

    fun isFulfilled(): Boolean {
        return period.isFulfilled(startTime, lastFulfillmentTime)
    }

}