package id.xxx.fake.gps.domain.auth.usecase

import id.xxx.fake.gps.domain.auth.model.UserModel
import id.xxx.fake.gps.domain.auth.repository.AuthRepository

interface IInteractor : AuthRepository<UserModel>