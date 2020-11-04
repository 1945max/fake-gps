package id.xxx.fake.gps.data.repository

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import id.xxx.data.source.firebase.auth.Resource
import id.xxx.data.source.firebase.auth.remote.ApiResponse
import id.xxx.data.source.firebase.auth.remote.ApiResponse.*
import id.xxx.fake.gps.domain.auth.model.User
import id.xxx.fake.gps.domain.auth.model.UserModel
import id.xxx.fake.gps.domain.auth.repository.IRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthRepository : IRepository<UserModel> {
    private val remote = FirebaseAuth.getInstance()

    override fun signOut() = remote.signOut()

    override fun getUser() = remote.currentUser?.let { user ->
        User.Exist(UserModel(user.uid, user.isEmailVerified))
    } ?: User.Empty

    override fun verifyEmail(scope: LifecycleCoroutineScope): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        val user = remote.currentUser
        user?.apply {
            sendEmailVerification()
            scope.launch {
                while (!isEmailVerified) {
                    delay(500)
                    remote.currentUser?.reload()
                    if (isEmailVerified) result.postValue(isEmailVerified)
                }
            }
        }
        return result
    }

    override fun sign(userName: String, pass: String) = flow {
        emit(Loading)
        try {
            emit(Success(remote.signInWithEmailAndPassword(userName, pass).await()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO).map { setResource(it) }

    override fun sign(token: String) = flow {
        emit(Loading)
        try {
            emit(Success(remote.signInWithCustomToken(token).await()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO).map { setResource(it) }

    override fun createUser(userName: String, pass: String) = flow {
        emit(Loading)
        try {
            emit(Success(remote.createUserWithEmailAndPassword(userName, pass).await()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO).map { setResource(it) }

    private fun setResource(apiResponse: ApiResponse<AuthResult>) = when (apiResponse) {
        is Loading -> Resource.Loading
        is Error -> Resource.Error(apiResponse.errorMessage)
        is Success -> apiResponse.data.user?.let { user ->
            Resource.Success(UserModel(user.uid, user.isEmailVerified))
        } ?: Resource.Empty
        is Empty -> Resource.Empty
    }
}