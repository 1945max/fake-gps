package id.xxx.fake.gps.domain.history.usecase

import id.xxx.fake.gps.domain.history.model.HistoryModel
import id.xxx.fake.gps.domain.history.repository.IRepository

class Interactor constructor(
    private val iExampleRepository: IRepository<HistoryModel>
) : IInteractor {

    override fun getPagingData() = iExampleRepository.getPagingData()
    override suspend fun insert(model: HistoryModel) = iExampleRepository.insert(model)
    override suspend fun delete(model: HistoryModel) = iExampleRepository.delete(model)

}