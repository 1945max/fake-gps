package id.xxx.fake.test.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import id.xxx.fake.test.data.local.history.LocalDataSource
import id.xxx.fake.test.domain.history.model.HistoryModel
import id.xxx.fake.test.domain.history.repository.IRepository
import id.xxx.fake.test.data.DataMapper.toHistoryEntity
import id.xxx.fake.test.data.DataMapper.toHistoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HistoryRepository(
    private val localDataSource: LocalDataSource
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
            config = PagingConfig(pageSize = 30, enablePlaceholders = false)
        ) { localDataSource.getPaging() }.flow.map {
            it.map { data -> data.toHistoryModel() }
        }
    }
}