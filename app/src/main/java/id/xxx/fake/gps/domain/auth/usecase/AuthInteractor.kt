package id.xxx.fake.gps.domain.auth.usecase

import id.xxx.fake.gps.domain.auth.model.UserModel
import id.xxx.fake.gps.domain.auth.repository.IAuthRepository

class AuthInteractor constructor(
    private val iRepository: IAuthRepository<UserModel>
) : IAuthUseCase {
    override fun getUser() = iRepository.getUser()

    override fun signOut() = iRepository.signOut()

    override fun verifyEmail() = iRepository.verifyEmail()

    override fun sign(userName: String, pass: String) = iRepository.sign(userName, pass)

    override fun sign(token: String) = iRepository.sign(token)

    override fun createUser(userName: String, pass: String) = iRepository.createUser(userName, pass)
}