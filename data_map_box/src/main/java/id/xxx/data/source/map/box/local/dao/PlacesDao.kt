package id.xxx.data.source.map.box.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import id.xxx.data.source.map.box.local.entity.PlacesEntity
import id.xxx.data.source.map.box.local.entity.PlacesEntity.Companion.SH_ADDRESS
import id.xxx.data.source.map.box.local.entity.PlacesEntity.Companion.SH_ID
import id.xxx.data.source.map.box.local.entity.PlacesEntity.Companion.SH_LATITUDE
import id.xxx.data.source.map.box.local.entity.PlacesEntity.Companion.SH_LONGITUDE
import id.xxx.data.source.map.box.local.entity.PlacesEntity.Companion.SH_NAME
import id.xxx.data.source.map.box.local.entity.PlacesEntity.Companion.SH_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface PlacesDao : BaseDao<PlacesEntity> {

    @Query("SELECT * FROM $SH_TABLE WHERE $SH_ID=:id")
    fun getByID(id: Int): PlacesEntity

    @Query("SELECT * FROM $SH_TABLE WHERE $SH_NAME LIKE '%' || :value || '%' OR $SH_ADDRESS LIKE '%' || :value || '%' ORDER BY $SH_ID DESC")
    fun getPaged(value: String): PagingSource<Int, PlacesEntity>

    @Query("SELECT * FROM $SH_TABLE WHERE $SH_NAME LIKE '%' || :value || '%' OR $SH_ADDRESS LIKE '%' || :value || '%' ORDER BY $SH_ID DESC")
    fun likeColumnNameOrAddress(value: String): Flow<List<PlacesEntity>>

    @Query("SELECT ${SH_TABLE}.* FROM $SH_TABLE WHERE $SH_NAME=:value OR $SH_ADDRESS=:value ORDER BY $SH_ID DESC")
    fun selectInColumnNameOrAddress(value: String): PlacesEntity?

    @Query("SELECT ${SH_TABLE}.* FROM $SH_TABLE WHERE $SH_LATITUDE LIKE :latitude AND $SH_LONGITUDE LIKE :longitude ORDER BY $SH_ID DESC")
    fun selectInColumnNameOrAddress(latitude: Double, longitude: Double): PlacesEntity?

    @Query("SELECT DISTINCT $SH_NAME FROM $SH_TABLE WHERE $SH_NAME LIKE '%' || :value || '%'")
    fun distinctColumnName(value: String?): List<String>

    @Query("SELECT DISTINCT $SH_NAME FROM $SH_TABLE")
    fun distinctColumnName(): List<String>
}