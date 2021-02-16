package id.xxx.fake.test.domain.search.model

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class SearchModel(
    var id: Int,
    var name: String,
    var address: String,
    var latitude: Double,
    var longitude: Double,
    var date: Long = System.currentTimeMillis()
) : Parcelable