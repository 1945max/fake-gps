package id.xxx.data.source.firebase.firestore.history.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import id.xxx.data.source.firebase.firestore.history.local.entity.HistoryEntity
import id.xxx.data.source.firebase.firestore.history.local.entity.HistoryEntity.Companion.FLH_ID
import id.xxx.data.source.firebase.firestore.history.local.entity.HistoryEntity.Companion.FLH_TABLE

@Dao
interface HistoryDao : BaseDao<HistoryEntity> {

    @Query("SELECT ${FLH_TABLE}.* FROM $FLH_TABLE ORDER BY $FLH_ID DESC")
    fun getPagingSource(): PagingSource<Int, HistoryEntity>

    @Query("DELETE FROM $FLH_TABLE")
    suspend fun clear()
}