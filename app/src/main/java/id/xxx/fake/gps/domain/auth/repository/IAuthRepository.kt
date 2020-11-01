package id.xxx.fake.gps.domain.auth.repository

import id.xxx.data.source.firebase.auth.Resource
import kotlinx.coroutines.flow.Flow

interface IAuthRepository<Model> {
    fun getUser(): Model?
    fun signOut()
    fun verifyEmail(): Flow<Boolean>
    fun sign(userName: String, pass: String): Flow<Resource<Model>>
    fun sign(token: String): Flow<Resource<Model>>
    fun createUser(userName: String, pass: String): Flow<Resource<Model>>
}