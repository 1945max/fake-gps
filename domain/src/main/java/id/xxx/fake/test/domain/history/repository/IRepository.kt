package id.xxx.fake.test.domain.history.repository

import androidx.paging.PagingData
import id.xxx.fake.test.domain.halper.BaseModel
import kotlinx.coroutines.flow.Flow

interface IRepository<Model : BaseModel<*>> {
    fun getHistory(): Flow<PagingData<Model>>
    suspend fun insert(model: Model)
    suspend fun delete(model: Model)
    suspend fun clear()
}