package id.xxx.fake.gps.domain.auth.usecase

import androidx.lifecycle.LifecycleCoroutineScope
import id.xxx.fake.gps.domain.auth.model.UserModel
import id.xxx.fake.gps.domain.auth.repository.IRepository

class Interactor constructor(
    private val iRepository: IRepository<UserModel>
) : IInteractor {
    override fun getUser() = iRepository.getUser()

    override fun signOut() = iRepository.signOut()

    override fun verifyEmail(scope: LifecycleCoroutineScope) = iRepository.verifyEmail(scope)

    override fun sign(userName: String, pass: String) = iRepository.sign(userName, pass)

    override fun sign(token: String) = iRepository.sign(token)

    override fun createUser(userName: String, pass: String) = iRepository.createUser(userName, pass)
}