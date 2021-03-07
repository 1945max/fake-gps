package id.xxx.auth.data.source.repository

import com.google.firebase.auth.FirebaseAuth
import id.xxx.auth.domain.model.User
import id.xxx.auth.domain.repository.IRepository
import id.xxx.base.domain.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class RepositoryImpl(
    private val remote: FirebaseAuth
) : IRepository {

    override fun createUser(userName: String, password: String) = flow {
        emit(Resource.Loading)

        val result = remote.createUserWithEmailAndPassword(userName, password).await()
        val user = result.user
        if (user == null) {
            emit(Resource.Empty)
        } else {
            emit(Resource.Success(user.toUser(User.LoginMode.WITH_EMAIL)))
        }
    }.catch { emit(Resource.Error(data = null, error = it)) }
        .flowOn(Dispatchers.IO)

    override fun signOut() {
        remote.signOut()
    }

    override fun sendEmailVerify() = flow {
        remote.currentUser?.sendEmailVerification()?.await()
        emit(true)
    }.catch { emit(false) }
        .flowOn(Dispatchers.IO)

    override fun emailIsVerify() = flow {
        val a = remote.currentUser
        a?.reload()?.await()
        emit(a?.isEmailVerified ?: false)
    }.catch { emit(false) }
        .flowOn(Dispatchers.IO)

    override fun currentUser() = flow {
        emit(Resource.Loading)
        remote.currentUser?.reload()?.await()
        val user = remote.currentUser
        if (user == null) {
            emit(Resource.Empty)
        } else {
            emit(Resource.Success(user.toUser(User.LoginMode.WITH_EMAIL)))
        }
    }.catch { emit(Resource.Error(error = it)) }.flowOn(Dispatchers.IO)

    override fun signIn(userName: String, password: String) = flow {
        emit(Resource.Loading)
        val result = remote.signInWithEmailAndPassword(userName, password).await()
        val user = result.user
        if (user == null) {
            emit(Resource.Empty)
        } else {
            emit(Resource.Success(user.toUser(User.LoginMode.WITH_EMAIL)))
        }
    }.catch {
        emit(Resource.Error(error = it))
    }.flowOn(Dispatchers.IO)
}