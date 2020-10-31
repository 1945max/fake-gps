package id.xxx.data.source.firebase.auth.remote

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RemoteDataSource {
    private val auth = FirebaseAuth.getInstance()

    fun createUser(userName: String, pass: String): Flow<ApiResponse<AuthResult>> = flow {
        try {
            emit(ApiResponse.Success(auth.createUserWithEmailAndPassword(userName, pass).await()))
            suspend fun a() = coroutineScope {
                val auth = FirebaseAuth.getInstance()
                return@coroutineScope withContext(Dispatchers.IO) {
                    auth.signInWithEmailAndPassword("roottingandroid@gmail.com", "19021992")
                }.await()
            }
        } catch (e: Exception) {
            ApiResponse.Error(e.message.toString())
        }
    }.flowOn(Dispatchers.IO)

    fun signOut() = auth.signOut()

    fun getUser() = auth.currentUser

    fun verifyEmail() = flow {
        auth.currentUser?.apply {
            sendEmailVerification()
            while (!isEmailVerified) {
                delay(500); reload()
            }
            emit(true)
        } ?: emit(false)
    }.flowOn(Dispatchers.IO)

    fun signInWithEmailAndPass(userName: String, pass: String) = flow {
        try {
            emit(ApiResponse.Success(auth.signInWithEmailAndPassword(userName, pass).await()))
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun signWithToken(token: String) = flow {
        try {
            emit(ApiResponse.Success(auth.signInWithCustomToken(token).await()))
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)
}