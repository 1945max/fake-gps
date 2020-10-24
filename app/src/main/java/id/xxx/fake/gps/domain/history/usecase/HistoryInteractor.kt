package id.xxx.fake.gps.domain.history.usecase

import androidx.paging.PagingData
import id.xxx.data.source.fake.gps.Resource
import id.xxx.fake.gps.domain.history.model.HistoryModel
import id.xxx.fake.gps.domain.history.repository.IHistoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class HistoryInteractor constructor(
    private val iExampleRepository: IHistoryRepository<HistoryModel>
) : IHistoryUseCase {
    override fun getList(): Flow<Resource<List<HistoryModel>>> =
        iExampleRepository.getList()

    override fun getPagingData(scope: CoroutineScope): Flow<Resource<PagingData<HistoryModel>>> =
        iExampleRepository.getPagingData(scope)

    override fun insert(model: HistoryModel) = iExampleRepository.insert(model)

    override fun delete(model: HistoryModel) = iExampleRepository.delete(model)
}