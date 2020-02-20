package de.anew.persistency

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import de.anew.models.task.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM task WHERE id = :taskId")
    fun load(taskId: Long): LiveData<Task>

    @Query("SELECT * FROM task")
    fun loadAll(): LiveData<List<Task>>

    @Insert(onConflict = REPLACE)
    fun save(task: Task)

    @Delete
    fun delete(task: Task)

}