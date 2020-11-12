package id.xxx.fake.gps.domain.auth.repository

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LiveData
import id.xxx.data.source.firebase.auth.Resource
import id.xxx.fake.gps.domain.auth.model.User
import id.xxx.fake.gps.domain.auth.model.UserModel
import kotlinx.coroutines.flow.Flow

interface AuthRepository<Model> {
    fun getUser(): User<UserModel>
    fun signOut()
    fun verifyEmail(scope: LifecycleCoroutineScope): LiveData<Boolean>
    fun sign(userName: String, pass: String): Flow<Resource<Model>>
    fun sign(token: String): Flow<Resource<Model>>
    fun createUser(userName: String, pass: String): Flow<Resource<Model>>
    fun sendEmailVerifyAsFlow(): Flow<Boolean>
    fun sendEmailVerify()
    fun reload(): Flow<Void?>
}