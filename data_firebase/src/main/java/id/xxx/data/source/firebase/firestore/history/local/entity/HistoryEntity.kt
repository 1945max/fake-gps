package id.xxx.data.source.firebase.firestore.history.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.xxx.data.source.firebase.firestore.history.local.entity.HistoryEntity.Companion.FLH_TABLE

@Entity(
    tableName = FLH_TABLE,
    indices = [
//        Index(value = [FLH_ADDRESS], unique = true)
    ]
)
data class HistoryEntity(
    @PrimaryKey
    @ColumnInfo(name = FLH_ID)
    var id: String,

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