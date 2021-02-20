package id.xxx.fake.test.domain.auth.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
        val uid: String,
        val isEmailVerified: Boolean
) : Parcelable