package id.xxx.fake.test.domain.auth.usecase

import id.xxx.fake.test.domain.auth.model.UserModel
import id.xxx.fake.test.domain.auth.repository.AuthRepository

interface IInteractor : AuthRepository<UserModel>