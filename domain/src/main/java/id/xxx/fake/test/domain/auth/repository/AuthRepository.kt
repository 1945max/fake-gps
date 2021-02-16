package id.xxx.fake.test.domain.auth.repository

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LiveData
import id.xxx.fake.test.domain.auth.model.User
import id.xxx.fake.test.domain.auth.model.UserModel
import id.xxx.fake.test.domain.halper.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository<Model> {
    fun getUser(): User<UserModel>
    suspend fun signOut()
    fun verifyEmail(scope: LifecycleCoroutineScope): LiveData<Boolean>
    fun sign(userName: String, pass: String): Flow<Resource<Model>>
    fun sign(token: String): Flow<Resource<Model>>
    fun createUser(userName: String, pass: String): Flow<Resource<Model>>
    fun sendEmailVerifyAsFlow(): Flow<Boolean>
    fun sendEmailVerify()
    fun reload(): Flow<Void?>
}