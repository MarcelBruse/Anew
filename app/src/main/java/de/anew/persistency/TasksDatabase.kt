/*
 * Copyright 2020 Marcel Bruse
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.anew.persistency

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import de.anew.R
import de.anew.models.task.Task
import de.anew.models.time.Weekly
import org.threeten.bp.ZonedDateTime
import kotlin.concurrent.thread

@Database(entities = [Task::class], version = 1)
abstract class TasksDatabase : RoomDatabase() {

    abstract fun getTaskDao(): TaskDao

    companion object {

        private const val DATABASE_NAME = "Tasks Database"

        @Volatile private var instance: TasksDatabase? = null

        fun getInstance(context: Context): TasksDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): TasksDatabase {
            return Room.databaseBuilder(context, TasksDatabase::class.java, DATABASE_NAME)
                .addCallback(getDatabaseCallback(context))
                .build()
        }

        private fun getDatabaseCallback(context: Context): Callback {
            return object : RoomDatabase.Callback() {
                override fun onCreate(database: SupportSQLiteDatabase) {
                    super.onCreate(database)
                    thread {
                        val nameOfFirstTask = context.getString(R.string.name_of_first_task)
                        val task = Task(0L, nameOfFirstTask, Weekly(), ZonedDateTime.now(), null)
                        val taskDao = getInstance(context).getTaskDao()
                        taskDao.save(task)
                    }
                }
            }
        }

    }

}

