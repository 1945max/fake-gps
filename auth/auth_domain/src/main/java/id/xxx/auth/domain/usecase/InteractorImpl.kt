package id.xxx.auth.domain.usecase

import id.xxx.auth.domain.repository.IRepository

class InteractorImpl(private val iRepository: IRepository) : IIntractor {

    override fun createUser(userName: String, password: String) =
        iRepository.createUser(userName, password)

    override fun signOut() = iRepository.signOut()

    override fun sendEmailVerify() = iRepository.sendEmailVerify()

    override fun isEmailVerify() = iRepository.emailIsVerify()

    override fun currentUser() = iRepository.currentUser()

    override fun signIn(userName: String, password: String) = iRepository.signIn(userName, password)
}