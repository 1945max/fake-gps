package id.xxx.fake.gps.history.domain.model

import android.os.Parcelable
import id.xxx.base.domain.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryModel(
    override val id: Long? = null,
    val address: String = "-",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val date: Long = System.currentTimeMillis()
) : Parcelable, BaseModel<Long>