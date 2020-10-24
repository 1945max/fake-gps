package id.xxx.fake.gps.domain.history.usecase

import id.xxx.fake.gps.domain.history.model.HistoryModel
import id.xxx.fake.gps.domain.history.repository.IHistoryRepository

interface IHistoryUseCase : IHistoryRepository<HistoryModel>