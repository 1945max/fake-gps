package id.xxx.fake.test.data.local.history

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface Dao {
    @Insert
    suspend fun insert(entity: HistoryEntity): Long

    @Query("DELETE FROM ${HistoryEntity.FLH_TABLE}")
    suspend fun clear()

    @Query("DELETE FROM ${HistoryEntity.FLH_TABLE} WHERE FLH_ID=:id")
    suspend fun delete(id: Long): Int

    @Query("SELECT ${HistoryEntity.FLH_TABLE}.* FROM ${HistoryEntity.FLH_TABLE} ORDER BY ${HistoryEntity.FLH_DATE} DESC")
    fun getPaging(): PagingSource<Int, HistoryEntity>
}