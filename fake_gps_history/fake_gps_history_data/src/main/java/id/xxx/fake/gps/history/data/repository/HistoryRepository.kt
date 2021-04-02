package id.xxx.fake.gps.history.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import id.xxx.fake.gps.history.data.mapper.DataMapper.toHistoryEntity
import id.xxx.fake.gps.history.data.mapper.DataMapper.toHistoryModel
import id.xxx.fake.gps.history.data.source.local.LocalDataSource
import id.xxx.fake.gps.history.data.source.remote.RemoteDataSource
import id.xxx.fake.gps.history.domain.model.HistoryModel
import id.xxx.fake.gps.history.domain.repository.IRepository
import kotlinx.coroutines.flow.Flow
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

    override fun getHistory(): Flow<PagingData<HistoryModel>> {
        return Pager(
            config = PagingConfig(pageSize = 30, enablePlaceholders = false),
            pagingSourceFactory = { localDataSource.getPaging() }
        ).flow.map {
            it.map { data -> data.toHistoryModel() }
        }
    }
}