package id.xxx.fake.gps.domain.auth.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
        val uid: String,
        val isEmailVerified: Boolean
) : Parcelable