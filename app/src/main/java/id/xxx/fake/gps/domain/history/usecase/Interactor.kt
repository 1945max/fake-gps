package id.xxx.fake.gps.domain.history.usecase

import androidx.paging.PagingData
import id.xxx.data.source.fake.gps.Resource
import id.xxx.fake.gps.domain.history.model.HistoryModel
import id.xxx.fake.gps.domain.history.repository.IRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class Interactor constructor(
    private val iExampleRepository: IRepository<HistoryModel>
) : IInteractor {
    override fun getList(): Flow<Resource<List<HistoryModel>>> =
        iExampleRepository.getList()

    override fun getPagingData(scope: CoroutineScope): Flow<Resource<PagingData<HistoryModel>>> =
        iExampleRepository.getPagingData(scope)

    override suspend fun insert(model: HistoryModel) = iExampleRepository.insert(model)

    override suspend fun delete(model: HistoryModel) = iExampleRepository.delete(model)
}