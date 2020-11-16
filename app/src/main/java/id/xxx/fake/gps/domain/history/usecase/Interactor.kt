package id.xxx.fake.gps.domain.history.usecase

import id.xxx.fake.gps.domain.history.model.HistoryModel
import id.xxx.fake.gps.domain.history.repository.IRepository

class Interactor constructor(
    private val iRepository: IRepository<HistoryModel>
) : IInteractor {

    override fun getHistory() = iRepository.getHistory()
    override fun insert(model: HistoryModel) = iRepository.insert(model)
    override fun delete(model: HistoryModel) = iRepository.delete(model)
    override suspend fun clear() = iRepository.clear()

}