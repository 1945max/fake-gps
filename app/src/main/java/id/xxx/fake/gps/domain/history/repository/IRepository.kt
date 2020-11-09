package id.xxx.fake.gps.domain.history.repository

import androidx.paging.PagingData
import id.xxx.fake.gps.domain.history.model.HistoryModel
import kotlinx.coroutines.flow.Flow

interface IRepository<Model : Any> {
    fun getPagingData(): Flow<PagingData<HistoryModel>>
    suspend fun insert(model: Model): Long
    suspend fun delete(model: Model): Int
}