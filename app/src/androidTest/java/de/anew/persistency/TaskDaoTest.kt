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

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import de.anew.models.task.Task
import de.anew.models.time.Weekly
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.threeten.bp.Clock
import org.threeten.bp.ZonedDateTime
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class TaskDaoTest {

    private lateinit var database: TasksDatabase

    private lateinit var taskDao: TaskDao

    @Before
    fun createDatabase() {
        val context = InstrumentationRegistry.getInstrumentation().context
        database = Room.inMemoryDatabaseBuilder(context, TasksDatabase::class.java).build()
        taskDao = database.getTaskDao()
        taskDao.save(
            Task(
                TEST_TASK_ID,
                TEST_TASK_NAME,
                Weekly(Clock.systemDefaultZone()),
                TEST_START_TIME,
                TEST_LAST_FULFILLMENT_TIME
            )
        )
    }

    @Test
    fun queryForNonExistentTask() {
        val task = getValue(taskDao.load(12357L))
        assertThat(task).isNull()
    }

    @Test
    fun queryAllTasks() {
        taskDao.save(
            Task(
                0L,
                "First task",
                Weekly(Clock.systemDefaultZone()),
                SAMPLE_TIME,
                SAMPLE_TIME
            )
        )
        val tasks = getValue(taskDao.loadAll())
        assertThat(tasks).hasSize(2)
        var filterResult = tasks.filter { task -> task.id != 0L }
            .filter { task -> task.name != TEST_TASK_NAME }
        assertThat(filterResult).hasSize(1)
        filterResult = tasks.filter { task -> task.id != 1L }
            .filter { task -> task.name != "First task" }
        assertThat(filterResult).hasSize(1)
    }

    @Test
    fun saveTask() {
        val task = getValue(taskDao.load(TEST_TASK_ID))
        assertThat(task).isNotNull
        assertThat(task?.id).isEqualTo(TEST_TASK_ID)
        assertThat(task?.name).isEqualTo(TEST_TASK_NAME)
        assertThat(task?.period).isEqualTo(Weekly(Clock.systemDefaultZone()))
        assertThat(task?.startTime).isEqualTo(TEST_START_TIME)
        assertThat(task?.lastFulfillmentTime).isEqualTo(TEST_LAST_FULFILLMENT_TIME)
    }

    @Test
    fun autoGeneratePrimaryKeysUponTaskCreation() {
        val name = "First task"
        taskDao.save(
            Task(
                0L,
                name,
                Weekly(Clock.systemDefaultZone()),
                SAMPLE_TIME,
                SAMPLE_TIME
            )
        )
        val tasks = getValue(taskDao.loadAll())
        assertThat(tasks).hasSize(2)
        val filterResult = tasks.filter { task -> task.id != 0L }
            .filter { task -> task.id != TEST_TASK_ID }
        assertThat(filterResult).hasSize(1)
        val task = filterResult[0]
        assertThat(task.name).isEqualTo(name)
    }

    @Test
    fun saveTaskAndAlterItAndSaveItAgain() {
        val oldTask = getValue(taskDao.load(TEST_TASK_ID))
        assertThat(oldTask?.id).isEqualTo(TEST_TASK_ID)
        assertThat(oldTask?.name).isEqualTo(TEST_TASK_NAME)
        assertThat(oldTask?.period).isEqualTo(Weekly(Clock.systemDefaultZone()))
        assertThat(oldTask?.startTime).isEqualTo(TEST_START_TIME)
        assertThat(oldTask?.lastFulfillmentTime).isEqualTo(TEST_LAST_FULFILLMENT_TIME)
        assertThat(getValue(taskDao.loadAll())).hasSize(1)
        taskDao.save(
            Task(
                TEST_TASK_ID,
                TEST_TASK_NAME.reversed(),
                Weekly(Clock.systemDefaultZone()),
                TEST_START_TIME,
                TEST_LAST_FULFILLMENT_TIME
            )
        )
        val newTask = getValue(taskDao.load(TEST_TASK_ID))
        assertThat(newTask?.id).isEqualTo(TEST_TASK_ID)
        assertThat(newTask?.name).isEqualTo(TEST_TASK_NAME.reversed())
        assertThat(newTask?.period).isEqualTo(Weekly(Clock.systemDefaultZone()))
        assertThat(newTask?.startTime).isEqualTo(TEST_START_TIME)
        assertThat(newTask?.lastFulfillmentTime).isEqualTo(TEST_LAST_FULFILLMENT_TIME)
        assertThat(getValue(taskDao.loadAll())).hasSize(1)
    }

    @Test
    fun deleteTask() {
        val task = getValue(taskDao.load(TEST_TASK_ID))
        assertThat(task).isNotNull
        assertThat(task?.id).isEqualTo(TEST_TASK_ID)

        taskDao.delete(task!!)
        val noTask = getValue(taskDao.load(TEST_TASK_ID))
        assertThat(noTask).isNull()
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
    }

    private fun getValue(liveData: LiveData<Task>): Task? {
        var task: Task? = null
        val latch = CountDownLatch(1)
        Handler(Looper.getMainLooper()).post {
            liveData.observeForever { result ->
                task = result
                latch.countDown()
            }
        }
        latch.await(2, TimeUnit.SECONDS)
        return task
    }

    private fun getValue(liveData: LiveData<List<Task>>): Set<Task> {
        val tasks = mutableSetOf<Task>()
        val latch = CountDownLatch(1)
        Handler(Looper.getMainLooper()).post {
            liveData.observeForever { result ->
                tasks.addAll(result)
                latch.countDown()
            }
        }
        latch.await(2, TimeUnit.SECONDS)
        return tasks
    }

    companion object {
        private const val TEST_TASK_ID = 42L
        private const val TEST_TASK_NAME = "Task"
        private val TEST_START_TIME = ZonedDateTime.parse("2019-11-13T01:00:00+01:00[Europe/Berlin]")
        private val TEST_LAST_FULFILLMENT_TIME = ZonedDateTime.parse("2019-11-16T12:10:10+01:00[Europe/Berlin]")
        private val SAMPLE_TIME = ZonedDateTime.parse("2019-11-13T07:00:00+01:00[Europe/Berlin]")
    }

}
