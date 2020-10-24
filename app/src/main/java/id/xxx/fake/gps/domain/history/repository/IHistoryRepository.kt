package id.xxx.fake.gps.domain.history.repository

import androidx.paging.PagingData
import id.xxx.data.source.fake.gps.Resource
import id.xxx.fake.gps.domain.history.model.HistoryModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface IHistoryRepository<Model> {
    fun getList(): Flow<Resource<List<Model>>>
    fun getPagingData(scope: CoroutineScope): Flow<Resource<PagingData<HistoryModel>>>
    fun insert(model: Model)
    fun delete(model: Model)
}