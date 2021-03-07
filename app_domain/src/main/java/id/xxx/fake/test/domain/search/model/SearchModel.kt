package id.xxx.fake.test.domain.search.model

import android.os.Parcelable
import id.xxx.base.domain.model.BaseModel

@kotlinx.parcelize.Parcelize
data class SearchModel(
    override val id: Int,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val date: Long = System.currentTimeMillis()
) : Parcelable, BaseModel<Int>