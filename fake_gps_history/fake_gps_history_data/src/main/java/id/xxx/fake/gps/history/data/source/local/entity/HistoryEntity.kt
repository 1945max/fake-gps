package id.xxx.fake.gps.history.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.xxx.base.domain.model.BaseEntity
import id.xxx.fake.gps.history.data.source.local.entity.HistoryEntity.Companion.FLH_TABLE

@Entity(
    tableName = FLH_TABLE,
)
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = FLH_ID)
    override val id: Long? = null,

    val historyId: String? = null,

    val userId: String? = null,

    @ColumnInfo(name = FLH_ADDRESS)
    val address: String = "",

    @ColumnInfo(name = FLH_LATITUDE)
    val latitude: Double,

    @ColumnInfo(name = FLH_LONGITUDE)
    val longitude: Double,

    @ColumnInfo(name = FLH_DATE)
    val date: Long = System.currentTimeMillis()
) : BaseEntity<Long> {
    companion object {
        const val FLH_TABLE = "FAKE_LOCATION_HISTORY"
        const val FLH_ID = "FLH_ID"
        const val FLH_ADDRESS = "FLH_ADDRESS"
        const val FLH_LATITUDE = "FLH_LATITUDE"
        const val FLH_LONGITUDE = "FLH_LONGITUDE"
        const val FLH_DATE = "FLH_DATE"
    }
}