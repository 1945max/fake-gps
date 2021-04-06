package id.xxx.fake.gps.history.data.repository

import androidx.paging.*
import id.xxx.fake.gps.history.data.mapper.DataMapper.toHistoryEntity
import id.xxx.fake.gps.history.data.mapper.DataMapper.toHistoryModel
import id.xxx.fake.gps.history.data.source.local.LocalDataSource
import id.xxx.fake.gps.history.data.source.remote.RemoteDataSource
import id.xxx.fake.gps.history.domain.model.HistoryModel
import id.xxx.fake.gps.history.domain.repository.IRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class HistoryRepository(
    private val localDataSource: LocalDataSource,
    @Suppress("EXPERIMENTAL_API_USAGE")
    private val remoteDataSource: RemoteDataSource
) : IRepository<HistoryModel> {
    override suspend fun insert(model: HistoryModel) {
        localDataSource.insert(model.toHistoryEntity())
    }

    override suspend fun clear() = localDataSource.clear()

    override suspend fun delete(model: HistoryModel) {
        model.id?.apply { localDataSource.delete(this) }
    }

    @ExperimentalPagingApi
    override fun getHistory(userId: String?): Flow<PagingData<HistoryModel>> {
        if (userId.isNullOrBlank()) return flowOf()

        return Pager(
            config = PagingConfig(pageSize = 1, enablePlaceholders = true),
            remoteMediator = HistoryRemoteMediator(
                blockGetPage = { localDataSource.getLastDate()?.date },
                blockRequest = { remoteDataSource.getPage(userId, it) },
                blockOnRequest = {
                    localDataSource.insert(it.toTypedArray().map { it.toHistoryEntity() })
                }
            ),
            pagingSourceFactory = { localDataSource.getPaging() }
        ).flow.flowOn(Dispatchers.IO).map { it.map { it.toHistoryModel() } }
    }
}