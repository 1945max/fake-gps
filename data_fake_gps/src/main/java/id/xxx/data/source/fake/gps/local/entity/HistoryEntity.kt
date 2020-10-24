package id.xxx.data.source.fake.gps.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import id.xxx.data.source.fake.gps.local.entity.HistoryEntity.Companion.FLH_ADDRESS
import id.xxx.data.source.fake.gps.local.entity.HistoryEntity.Companion.FLH_TABLE
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(
    tableName = FLH_TABLE,
    indices = [
        Index(value = [FLH_ADDRESS], unique = true)
    ]
)
data class HistoryEntity(
    @PrimaryKey
    @ColumnInfo(name = FLH_ID)
    var id: Int? = null,

    @ColumnInfo(name = FLH_ADDRESS)
    var address: String = "",

    @ColumnInfo(name = FLH_LATITUDE)
    var latitude: Double,

    @ColumnInfo(name = FLH_LONGITUDE)
    var longitude: Double,

    @ColumnInfo(name = FLH_DATE)
    var date: Long = System.currentTimeMillis()
) : Parcelable {
    companion object {
        const val FLH_TABLE = "FAKE_LOCATION_HISTORY"
        const val FLH_ID = "FLH_ID"
        const val FLH_ADDRESS = "FLH_ADDRESS"
        const val FLH_LATITUDE = "FLH_LATITUDE"
        const val FLH_LONGITUDE = "FLH_LONGITUDE"
        const val FLH_DATE = "FLH_DATE"
    }
}