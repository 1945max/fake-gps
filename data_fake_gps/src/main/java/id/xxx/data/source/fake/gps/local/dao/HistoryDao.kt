package id.xxx.data.source.fake.gps.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import id.xxx.data.source.fake.gps.local.entity.HistoryEntity
import id.xxx.data.source.fake.gps.local.entity.HistoryEntity.Companion.FLH_ID
import id.xxx.data.source.fake.gps.local.entity.HistoryEntity.Companion.FLH_TABLE

@Dao
interface HistoryDao : BaseDao<HistoryEntity> {

    @Query("SELECT ${FLH_TABLE}.* FROM $FLH_TABLE ORDER BY $FLH_ID DESC")
    fun getPagingSource(): PagingSource<Int, HistoryEntity>
}