package de.quotas.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Quota(
    @PrimaryKey val id: Long,
    val name: String
)