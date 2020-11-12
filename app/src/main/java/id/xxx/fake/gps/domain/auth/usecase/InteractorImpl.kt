package id.xxx.fake.gps.domain.auth.usecase

import androidx.lifecycle.LifecycleCoroutineScope
import id.xxx.fake.gps.domain.auth.model.UserModel
import id.xxx.fake.gps.domain.auth.repository.AuthRepository

class InteractorImpl constructor(
        private val iRepository: AuthRepository<UserModel>
) : IInteractor {

    override fun getUser() = iRepository.getUser()
    override fun signOut() = iRepository.signOut()
    override fun verifyEmail(scope: LifecycleCoroutineScope) = iRepository.verifyEmail(scope)
    override fun sign(userName: String, pass: String) = iRepository.sign(userName, pass)
    override fun sign(token: String) = iRepository.sign(token)
    override fun createUser(userName: String, pass: String) = iRepository.createUser(userName, pass)
    override fun sendEmailVerifyAsFlow() = iRepository.sendEmailVerifyAsFlow()
    override fun sendEmailVerify() = iRepository.sendEmailVerify()
    override fun reload() = iRepository.reload()

}