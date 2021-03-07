package id.xxx.data.source.map.box.repository

import android.content.Context
import androidx.paging.*
import id.xxx.base.domain.model.Resource
import id.xxx.data.source.map.box.local.LocalDataSource
import id.xxx.data.source.map.box.local.entity.PlacesEntity
import id.xxx.data.source.map.box.remote.RemoteDataSource
import id.xxx.data.source.map.box.utils.Address
import id.xxx.data.source.map.box.utils.DataMapper.toListSearchEntity
import id.xxx.data.source.map.box.utils.DataMapper.toSearchModel
import id.xxx.data.source.map.box.utils.Result
import id.xxx.fake.test.domain.search.model.SearchModel
import id.xxx.fake.test.domain.search.repository.IRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

@ExperimentalPagingApi
class RepositoryImpl(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) : IRepository<SearchModel> {

    override fun getPlaceWithPagingData(query: String): Flow<PagingData<SearchModel>> = Pager(
        config = PagingConfig(pageSize = 10, enablePlaceholders = false),
        remoteMediator = RemoteMediator(query, remote.apiService, local)
    ) { local.getPaging(query) }.flow.map {
        return@map if (query.isBlank()) PagingData.empty() else it.map { searchEntity ->
            searchEntity.toSearchModel()
        }
    }

    override fun getPlaces(query: String) = id.xxx.base.domain.helper.networkBoundResource(
        loadFromDB = {
            local.search(query).map { it.map { data -> data.toSearchModel() } }
        },
        fetch = { remote.getPlaces(query) },
        saveFetchResult = { data ->
            local.insert(*toListSearchEntity.map(data.features).toTypedArray())
        }
    )

    override fun getAddress(context: Context, value: String) = flow {
        emit(Resource.Loading)
        val isLatLong: Boolean = Address.isLatLong(value)
        val data = ArrayList<Double>()
        val latitude = 0
        val longitude = 1
        if (isLatLong) data.addAll(value.split(",").map { it.toDouble() })
        when (val result = Address.getInstance(context).getData(value)) {
            is Result.Success -> {
                val id = local.insert(
                    PlacesEntity(
                        name = if (isLatLong) result.data.getAddressLine(0) else value,
                        latitude = if (isLatLong) data[latitude] else result.data.latitude,
                        longitude = if (isLatLong) data[longitude] else result.data.longitude,
                        address = result.data.getAddressLine(0),
                    )
                )
                emit(Resource.Success(toSearchModel.map(local.getByID(id.toInt()))))
            }
            is Result.Empty -> emit(Resource.Empty)
            is Result.Error -> {
                val searchEntity =
                    if (isLatLong)
                        local.select(data[latitude], data[longitude])
                    else
                        local.select(value)
                emit(
                    Resource.Error(
                        error = Throwable(result.message),
                        data = searchEntity?.let { toSearchModel.map(it) })
                )
            }
        }
    }.flowOn(Dispatchers.IO)

}