package id.xxx.fake.gps.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import id.xxx.data.source.fake.gps.local.LocalDataSource
import id.xxx.fake.gps.domain.history.model.HistoryModel
import id.xxx.fake.gps.domain.history.repository.IRepository
import id.xxx.fake.gps.utils.DataMapper.toHistoryEntity
import id.xxx.fake.gps.utils.DataMapper.toHistoryModel
import kotlinx.coroutines.flow.map

class HistoryRepository(
    private val local: LocalDataSource,
) : IRepository<HistoryModel> {

    override fun getPagingData() =
        Pager(config = PagingConfig(pageSize = 10, enablePlaceholders = false)) {
            local.getPagingSource()
        }.flow.map {
            it.map { historyEntity -> toHistoryModel.map(historyEntity) }
        }

    override suspend fun insert(model: HistoryModel) =
        local.insert(toHistoryEntity.map(model))

    override suspend fun delete(model: HistoryModel) =
        local.delete(toHistoryEntity.map(model))
}