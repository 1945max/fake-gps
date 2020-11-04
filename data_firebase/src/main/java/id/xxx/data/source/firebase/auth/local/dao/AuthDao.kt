package id.xxx.data.source.firebase.auth.local.dao

import androidx.room.Dao
import id.xxx.data.source.firebase.auth.local.entity.UserEntity

@Dao
interface AuthDao : BaseDao<UserEntity>