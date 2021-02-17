package id.xxx.fake.test.domain.history.usecase

import id.xxx.fake.test.domain.history.model.HistoryModel
import id.xxx.fake.test.domain.history.repository.IRepository

class InteractorImpl constructor(
    private val iRepository: IRepository<HistoryModel>
) : IInteractor {

    override fun getHistory() = iRepository.getHistory()
    override suspend fun insert(model: HistoryModel) = iRepository.insert(model)
    override suspend fun delete(model: HistoryModel) = iRepository.delete(model)
    override suspend fun clear() = iRepository.clear()

}