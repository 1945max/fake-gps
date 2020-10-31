package id.xxx.data.source.firebase.auth.local

import id.xxx.data.source.firebase.auth.local.dao.AuthDao
import id.xxx.data.source.firebase.auth.local.entity.UserEntity

class LocalDataSource(private val dao: AuthDao) {
    fun getUserWithFlow() = dao.getUserWithFlow()
    suspend fun insert(entity: UserEntity) = dao.insert(entity)

    fun getUser() = dao.getUser()
}