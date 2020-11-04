package id.xxx.fake.gps.domain.auth.usecase

import id.xxx.fake.gps.domain.auth.model.UserModel
import id.xxx.fake.gps.domain.auth.repository.IRepository

interface IInteractor : IRepository<UserModel>