package id.xxx.fake.test.data.local.history

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface Dao {
    @Insert
    suspend fun insert(entity: Entity): Long

    @Query("DELETE FROM ${Entity.FLH_TABLE}")
    suspend fun clear()

    @Query("DELETE FROM ${Entity.FLH_TABLE} WHERE FLH_ID=:id")
    suspend fun delete(id: Long): Int

    @Query("SELECT ${Entity.FLH_TABLE}.* FROM ${Entity.FLH_TABLE} ORDER BY ${Entity.FLH_DATE} DESC")
    fun getPaging(): PagingSource<Int, Entity>
}