package id.xxx.fake.gps.history.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.xxx.fake.gps.history.data.source.local.entity.HistoryEntity.Companion.FLH_TABLE

@Entity(
    tableName = FLH_TABLE,
)
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = FLH_ID)
    var id: Long? = null,

    @ColumnInfo(name = FLH_ADDRESS)
    var address: String = "",

    @ColumnInfo(name = FLH_LATITUDE)
    var latitude: Double,

    @ColumnInfo(name = FLH_LONGITUDE)
    var longitude: Double,

    @ColumnInfo(name = FLH_DATE)
    var date: Long = System.currentTimeMillis()
) {
    companion object {
        const val FLH_TABLE = "FAKE_LOCATION_HISTORY"
        const val FLH_ID = "FLH_ID"
        const val FLH_ADDRESS = "FLH_ADDRESS"
        const val FLH_LATITUDE = "FLH_LATITUDE"
        const val FLH_LONGITUDE = "FLH_LONGITUDE"
        const val FLH_DATE = "FLH_DATE"
    }
}