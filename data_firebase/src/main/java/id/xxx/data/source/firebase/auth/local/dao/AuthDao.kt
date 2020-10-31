package id.xxx.data.source.firebase.auth.local.dao

import androidx.room.Dao
import androidx.room.Query
import id.xxx.data.source.firebase.auth.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AuthDao : BaseDao<UserEntity> {

    @Query("SELECT * FROM UserEntity")
    fun getUserWithFlow(): Flow<UserEntity?>

    @Query("SELECT * FROM UserEntity")
    fun getUser(): UserEntity
}