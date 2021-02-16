package id.xxx.fake.test.domain.search.repository

import android.content.Context
import androidx.paging.PagingData
import id.xxx.fake.test.domain.halper.Resource
import id.xxx.fake.test.domain.search.model.SearchModel
import kotlinx.coroutines.flow.Flow

interface IRepository<Model> {
    fun getPlaceWithPagingData(value: String): Flow<PagingData<SearchModel>>
    fun getPlaces(value: String): Flow<Resource<List<Model>>>
    fun getAddress(context: Context, value: String): Flow<Resource<SearchModel>>
}