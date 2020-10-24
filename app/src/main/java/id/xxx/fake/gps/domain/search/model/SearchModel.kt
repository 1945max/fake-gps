package id.xxx.fake.gps.domain.search.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchModel(
    var id: Int,
    var name: String,
    var address: String,
    var latitude: Double,
    var longitude: Double,
    var date: Long = System.currentTimeMillis()
) : Parcelable