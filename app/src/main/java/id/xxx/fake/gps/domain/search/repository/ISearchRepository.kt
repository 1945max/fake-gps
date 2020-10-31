package id.xxx.fake.gps.domain.search.repository

import android.content.Context
import androidx.paging.PagingData
import id.xxx.data.source.map.box.Resource
import id.xxx.fake.gps.domain.search.model.SearchModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface ISearchRepository<Model> {
    fun getPagingData(value: String, scope: CoroutineScope): Flow<Resource<PagingData<SearchModel>>>
    fun getPlaces(value: String): Flow<Resource<List<Model>>>
    fun getAddress(context: Context, value: String): Flow<Resource<SearchModel>>
}