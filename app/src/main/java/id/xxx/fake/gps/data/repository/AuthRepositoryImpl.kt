package id.xxx.fake.gps.data.repository

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import id.xxx.data.source.firebase.auth.Resource
import id.xxx.data.source.firebase.auth.remote.ApiResponse
import id.xxx.data.source.firebase.auth.remote.ApiResponse.*
import id.xxx.fake.gps.domain.auth.model.User
import id.xxx.fake.gps.domain.auth.model.UserModel
import id.xxx.fake.gps.domain.auth.repository.AuthRepository
import id.xxx.fake.gps.domain.history.usecase.IInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@ExperimentalCoroutinesApi
class AuthRepositoryImpl(
    private val localDataSource: IInteractor
) : AuthRepository<UserModel> {
    private val remote = FirebaseAuth.getInstance()

    private fun currentUser() = try {
        remote.currentUser
    } catch (e: FirebaseAuthInvalidUserException) {
        null
    }

    override suspend fun signOut() {
        localDataSource.clear()
        remote.signOut()
    }

    override fun getUser() = remote.currentUser?.let { user ->
        User.Exist(UserModel(user.uid, user.isEmailVerified))
    } ?: User.Empty


    override fun sendEmailVerifyAsFlow() = flow {
        try {
            currentUser()?.sendEmailVerification()?.await()
            emit(true)
        } catch (e: Exception) {
            emit(false)
        }
    }

    override fun verifyEmail(scope: LifecycleCoroutineScope): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        val user = currentUser()
        user?.apply {
            sendEmailVerification()
            scope.launch {
                while (!isEmailVerified) {
                    delay(500)
                    currentUser()?.reload()
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
            emit(Error(e))
        }
    }.flowOn(Dispatchers.IO).map { setResource(it) }

    override fun sign(token: String) = flow {
        emit(Loading)
        try {
            emit(Success(remote.signInWithCustomToken(token).await()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Error(e))
        }
    }.flowOn(Dispatchers.IO).map { setResource(it) }

    override fun createUser(userName: String, pass: String) = flow {
        emit(Loading)
        try {
            emit(Success(remote.createUserWithEmailAndPassword(userName, pass).await()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Error(e))
        }
    }.flowOn(Dispatchers.IO).map { setResource(it) }

    private fun setResource(apiResponse: ApiResponse<AuthResult>) = when (apiResponse) {
        is Loading -> Resource.Loading
        is Error -> Resource.Error(apiResponse.exception)
        is Success -> apiResponse.data.user?.let { user ->
            Resource.Success(UserModel(user.uid, user.isEmailVerified))
        } ?: Resource.Empty
        is Empty -> Resource.Empty
    }

    override fun sendEmailVerify() {
        currentUser()?.sendEmailVerification()
    }

    override fun reload() = flow {
        try {
            emit(currentUser()?.reload()?.await())
        } catch (e: Exception) {
            emit(null)
        }
    }
}