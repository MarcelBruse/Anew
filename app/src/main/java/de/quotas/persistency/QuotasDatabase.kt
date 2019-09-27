package de.quotas.persistency

import androidx.room.Database
import androidx.room.RoomDatabase
import de.quotas.models.Quota

@Database(entities = [Quota::class], version = 1)
abstract class QuotasDatabase : RoomDatabase() {

    abstract fun getQuotaDao(): QuotaDao

}