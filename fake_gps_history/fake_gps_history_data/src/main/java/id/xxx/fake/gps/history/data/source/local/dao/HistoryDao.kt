package id.xxx.fake.gps.history.data.source.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import id.xxx.fake.gps.history.data.source.local.entity.HistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert
    suspend fun insert(entity: HistoryEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: List<HistoryEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg entity: HistoryEntity): List<Long>

    @Query("DELETE FROM ${HistoryEntity.FLH_TABLE}")
    suspend fun clear()

    @Query("DELETE FROM ${HistoryEntity.FLH_TABLE} WHERE FLH_ID=:id")
    suspend fun delete(id: Long): Int

    @Query("SELECT ${HistoryEntity.FLH_TABLE}.* FROM ${HistoryEntity.FLH_TABLE} ORDER BY ${HistoryEntity.FLH_ID} ASC")
    fun getPaging(): PagingSource<Int, HistoryEntity>

    @Query("SELECT * FROM FAKE_LOCATION_HISTORY ORDER BY FLH_ID DESC")
    suspend fun getLastDate(): HistoryEntity?

    @Query("SELECT ${HistoryEntity.FLH_TABLE}.* FROM ${HistoryEntity.FLH_TABLE} ORDER BY ${HistoryEntity.FLH_ID} ASC")
    fun getList(): Flow<List<HistoryEntity>>
}