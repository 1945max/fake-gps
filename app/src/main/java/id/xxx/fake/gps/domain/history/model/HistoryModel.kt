package id.xxx.fake.gps.domain.history.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HistoryModel(
    var id: Int? = null,
    var address: String,
    var latitude: Double,
    var longitude: Double,
    var date: Long = System.currentTimeMillis()
) : Parcelable