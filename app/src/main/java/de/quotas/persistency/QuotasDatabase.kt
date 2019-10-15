package de.quotas.persistency

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import de.quotas.models.Quota
import kotlin.concurrent.thread

@Database(entities = [Quota::class], version = 1)
abstract class QuotasDatabase : RoomDatabase() {

    abstract fun getQuotaDao(): QuotaDao

    companion object {

        const val DATABASE_NAME = "Quotas Database"

        @Volatile private var instance: QuotasDatabase? = null

        fun getInstance(context: Context): QuotasDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): QuotasDatabase {
            return Room.databaseBuilder(context, QuotasDatabase::class.java, DATABASE_NAME)
                .addCallback(getDatabaseCallback(context))
                .build()
        }

        private fun getDatabaseCallback(context: Context): Callback {
            return object : RoomDatabase.Callback() {
                override fun onOpen(database: SupportSQLiteDatabase) {
                    super.onCreate(database)
                    thread {
                        val quotaDao = getInstance(context).getQuotaDao()
                        quotaDao.save(Quota(0L, "Some quota"))
                    }
                }
            }
        }



    }

}

