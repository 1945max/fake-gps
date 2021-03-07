package id.xxx.auth.domain.repository

import id.xxx.auth.domain.model.User
import id.xxx.base.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface IRepository {

    fun createUser(
        userName: String, password: String
    ): Flow<Resource<User>>

    fun signOut()

    fun sendEmailVerify(): Flow<Boolean>

    fun emailIsVerify(): Flow<Boolean>

    fun currentUser(): Flow<Resource<User>>

    fun signIn(userName: String, password: String): Flow<Resource<User>>
}