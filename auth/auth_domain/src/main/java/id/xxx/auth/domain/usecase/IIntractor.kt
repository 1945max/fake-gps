package id.xxx.auth.domain.usecase

import id.xxx.auth.domain.model.User
import id.xxx.base.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface IIntractor {

    fun createUser(
        userName: String,
        password: String
    ): Flow<Resource<User>>

    fun isEmailVerify(): Flow<Boolean>

    fun signOut()

    fun sendEmailVerify(): Flow<Boolean>

    fun currentUser(): Flow<Resource<User>>

    fun signIn(userName: String, password: String): Flow<Resource<User>>

}