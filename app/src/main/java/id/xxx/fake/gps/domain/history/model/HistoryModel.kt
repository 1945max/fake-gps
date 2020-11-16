package id.xxx.fake.gps.domain.history.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HistoryModel(
    var id: String = "",
    var address: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var date: Long = System.currentTimeMillis()
) : Parcelable