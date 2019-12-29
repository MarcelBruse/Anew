package de.quotas.persistency

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import de.quotas.models.Quota

@Dao
interface QuotaDao {

    @Query("SELECT * FROM quota WHERE id = :quotaId")
    fun load(quotaId: Long): Quota

    @Query("SELECT * FROM quota")
    fun loadAll(): List<Quota>

    @Insert(onConflict = REPLACE)
    fun save(quota: Quota)

    @Query("SELECT * FROM quota WHERE id = :quotaId")
    fun loadAsLiveData(quotaId: Long): LiveData<Quota>

    @Query("SELECT * FROM quota")
    fun loadAllAsLiveData(): LiveData<List<Quota>>

    @Delete
    fun delete(quota: Quota)

}