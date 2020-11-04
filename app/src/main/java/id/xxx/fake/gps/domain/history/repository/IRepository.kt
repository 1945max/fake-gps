package id.xxx.fake.gps.domain.history.repository

import androidx.paging.PagingData
import id.xxx.data.source.fake.gps.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface IRepository<Model : Any> {
    fun getList(): Flow<Resource<List<Model>>>
    fun getPagingData(scope: CoroutineScope): Flow<Resource<PagingData<Model>>>
    suspend fun insert(model: Model)
    suspend fun delete(model: Model)
}