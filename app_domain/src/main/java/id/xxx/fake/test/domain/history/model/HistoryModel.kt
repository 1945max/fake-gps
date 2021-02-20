package id.xxx.fake.test.domain.history.model

import android.os.Parcelable
import com.base.model.BaseModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class HistoryModel(
    override val id: Long? = null,
    val address: String = "-",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val date: Long = System.currentTimeMillis()
) : Parcelable, BaseModel<Long>