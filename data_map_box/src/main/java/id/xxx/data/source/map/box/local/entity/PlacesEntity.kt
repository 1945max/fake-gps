package id.xxx.data.source.map.box.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import id.xxx.data.source.map.box.local.entity.PlacesEntity.Companion.SH_NAME
import id.xxx.data.source.map.box.local.entity.PlacesEntity.Companion.SH_TABLE

@Entity(
    tableName = SH_TABLE,
    indices = [
        Index(value = [SH_NAME], unique = true)
    ]
)
data class PlacesEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = SH_ID)
    var id: Int? = null,

    @ColumnInfo(name = SH_NAME)
    var name: String = "",

    @ColumnInfo(name = SH_ADDRESS)
    var address: String,

    @ColumnInfo(name = SH_LATITUDE)
    var latitude: Double,

    @ColumnInfo(name = SH_LONGITUDE)
    var longitude: Double,

    @ColumnInfo(name = SH_DATE)
    var date: Long = System.currentTimeMillis()
) {
    companion object {
        const val SH_TABLE = "SEARCH_HISTORY"
        const val SH_ID = "SH_ID"
        const val SH_NAME = "SH_NAME"
        const val SH_ADDRESS = "SH_ADDRESS"
        const val SH_LATITUDE = "SH_LATITUDE"
        const val SH_LONGITUDE = "SH_LONGITUDE"
        const val SH_DATE = "SH_DATE"
    }
}