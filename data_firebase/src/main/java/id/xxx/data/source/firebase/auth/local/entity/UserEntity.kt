package id.xxx.data.source.firebase.auth.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [
        Index(value = ["uid"], unique = true)
    ]
)
data class UserEntity(
    @PrimaryKey
    val uid: String,
)