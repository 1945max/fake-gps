package id.xxx.fake.gps.domain.history.repository

import androidx.paging.PagingData
import id.xxx.fake.gps.domain.history.model.HistoryModel
import kotlinx.coroutines.flow.Flow

interface IRepository<Model : Any> {
    fun getHistory(): Flow<PagingData<HistoryModel>>
    fun insert(model: Model)
    fun delete(model: Model)
}