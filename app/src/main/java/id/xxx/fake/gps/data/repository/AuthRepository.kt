package id.xxx.fake.gps.data.repository

import com.google.firebase.auth.AuthResult
import id.xxx.data.source.firebase.auth.Resource
import id.xxx.data.source.firebase.auth.remote.ApiResponse
import id.xxx.data.source.firebase.auth.remote.RemoteDataSource
import id.xxx.fake.gps.domain.auth.model.UserModel
import id.xxx.fake.gps.domain.auth.repository.IAuthRepository
import kotlinx.coroutines.flow.map

class AuthRepository(
    private val remote: RemoteDataSource
) : IAuthRepository<UserModel> {

    override fun signOut() = remote.signOut()

    override fun getUser() = remote.getUser()?.let {
        UserModel(it.uid, it.isEmailVerified)
    }

    override fun verifyEmail() = remote.verifyEmail()

    override fun sign(userName: String, pass: String) =
        remote.signInWithEmailAndPass(userName, pass).map { setResource(it) }

    override fun sign(token: String) =
        remote.signWithToken(token).map { setResource(it) }

    override fun createUser(userName: String, pass: String) =
        remote.createUser(userName, pass).map { setResource(it) }

    private fun setResource(apiResponse: ApiResponse<AuthResult>) =
        when (apiResponse) {
            is ApiResponse.Loading -> Resource.Loading
            is ApiResponse.Error -> Resource.Error(apiResponse.errorMessage)
            is ApiResponse.Success -> apiResponse.data.user?.let { user ->
                Resource.Success(UserModel(user.uid, user.isEmailVerified))
            } ?: Resource.Empty
        }
}