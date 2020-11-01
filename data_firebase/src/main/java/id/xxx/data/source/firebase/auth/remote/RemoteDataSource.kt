package id.xxx.data.source.firebase.auth.remote

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class RemoteDataSource {
    private val auth = FirebaseAuth.getInstance()

    fun createUser(userName: String, pass: String): Flow<ApiResponse<AuthResult>> = flow {
        emit(ApiResponse.Loading)
        try {
            emit(ApiResponse.Success(auth.createUserWithEmailAndPassword(userName, pass).await()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ApiResponse.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun signOut() = auth.signOut()

    fun getUser() = auth.currentUser

    fun verifyEmail() = flow {
        auth.currentUser?.apply {
           sendEmailVerification()
//            val start = System.currentTimeMillis()
            while (!isEmailVerified) {
                delay(300)
                reload()
//                if ((MINUTES.convert(System.currentTimeMillis() - start, MILLISECONDS)) == 5) {
//                    emit(false)
//                    return@apply
//                }
            }
            emit(true)
        } ?: emit(false)
    }.flowOn(Dispatchers.IO)

    fun signInWithEmailAndPass(userName: String, pass: String) = flow {
        emit(ApiResponse.Loading)
        try {
            emit(ApiResponse.Success(auth.signInWithEmailAndPassword(userName, pass).await()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ApiResponse.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun signWithToken(token: String) = flow {
        emit(ApiResponse.Loading)
        try {
            emit(ApiResponse.Success(auth.signInWithCustomToken(token).await()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ApiResponse.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)
}