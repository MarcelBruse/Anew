package de.quotas.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Quota(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val creationTime: Long,
    val lastFulfillmentTime: Long
)