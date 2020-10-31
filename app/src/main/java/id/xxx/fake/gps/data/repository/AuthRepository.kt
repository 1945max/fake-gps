package id.xxx.fake.gps.data.repository

import id.xxx.data.source.firebase.auth.Resource
import id.xxx.data.source.firebase.auth.local.LocalDataSource
import id.xxx.data.source.firebase.auth.remote.ApiResponse
import id.xxx.data.source.firebase.auth.remote.RemoteDataSource
import id.xxx.fake.gps.domain.auth.model.UserModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AuthRepository(private val local: LocalDataSource, private val remote: RemoteDataSource) {

    fun signOut() = remote.signOut()

    fun getUser() = remote.getUser()?.let {
        UserModel(it.uid, it.isEmailVerified)
    }

    suspend fun verifyEmail() = remote.verifyEmail().first()

    fun login(username: String, password: String) =
        remote.signInWithEmailAndPass(username, password).map {
            return@map when (it) {
                is ApiResponse.Loading -> Resource.Loading
                is ApiResponse.Success -> {
                    it.data.user?.let { user ->
                        Resource.Success(UserModel(user.uid, user.isEmailVerified))
                    } ?: Resource.Empty
                }
                is ApiResponse.Error -> Resource.Error(it.errorMessage)
            }
        }

    fun login(token: String) =
        remote.signWithToken(token).map {
            return@map when (it) {
                is ApiResponse.Loading -> Resource.Loading
                is ApiResponse.Success -> {
                    it.data.user?.let { user ->
                        Resource.Success(UserModel(user.uid, user.isEmailVerified))
                    } ?: Resource.Empty
                }
                is ApiResponse.Error -> Resource.Error(it.errorMessage)
            }
        }

    fun createUser(username: String, password: String) =
        remote.createUser(username, password).map {
            return@map when (it) {
                is ApiResponse.Loading -> Resource.Loading
                is ApiResponse.Success -> it.data.user?.let { user ->
                    Resource.Success(UserModel(user.uid, user.isEmailVerified))
                } ?: Resource.Empty
                is ApiResponse.Error -> Resource.Error(it.errorMessage)
            }
        }
}