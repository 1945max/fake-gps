package id.xxx.fake.gps.data.repository

import androidx.paging.*
import id.xxx.data.source.fake.gps.NetworkBoundResource
import id.xxx.data.source.fake.gps.Resource
import id.xxx.data.source.fake.gps.local.LocalDataSource
import id.xxx.data.source.fake.gps.network.ApiResponse
import id.xxx.fake.gps.domain.history.model.HistoryModel
import id.xxx.fake.gps.domain.history.repository.IHistoryRepository
import id.xxx.fake.gps.utils.DataMapper.toHistoryEntity
import id.xxx.fake.gps.utils.DataMapper.toHistoryModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class HistoryRepository constructor(
    private val historyDataSource: LocalDataSource,
) : IHistoryRepository<HistoryModel> {

    override fun getList(): Flow<Resource<List<HistoryModel>>> =
        object : NetworkBoundResource<List<HistoryModel>, Unit>() {
            override fun loadFromDB(): Flow<List<HistoryModel>> =
                historyDataSource.getHistories()
                    .map { historyEntity -> historyEntity.map { toHistoryModel.map(it) } }

            override fun shouldFetch(data: List<HistoryModel>?): Boolean = false
            override suspend fun createCall(): Flow<ApiResponse<Unit>> = flowOf()
            override suspend fun saveCallResult(data: Unit) {}
        }.asFlow()

    override fun getPagingData(scope: CoroutineScope): Flow<Resource<PagingData<HistoryModel>>> =
        object : NetworkBoundResource<PagingData<HistoryModel>, Unit>() {
            override fun loadFromDB(): Flow<PagingData<HistoryModel>> =
                Pager(config = PagingConfig(pageSize = 10, enablePlaceholders = false)) {
                    historyDataSource.getDataSourceFactory()
                }.flow.map {
                    it.map { historyEntity -> toHistoryModel.map(historyEntity) }
                }.cachedIn(scope)

            override fun shouldFetch(data: PagingData<HistoryModel>?): Boolean = false
            override suspend fun createCall(): Flow<ApiResponse<Unit>> = flowOf()
            override suspend fun saveCallResult(data: Unit) {}
        }.asFlow()

    override suspend fun insert(model: HistoryModel) {
        historyDataSource.insert(toHistoryEntity.map(model))
    }

    override suspend fun delete(model: HistoryModel) {
        historyDataSource.delete(toHistoryEntity.map(model))
    }
}