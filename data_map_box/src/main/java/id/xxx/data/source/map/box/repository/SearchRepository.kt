package id.xxx.data.source.map.box.repository

import android.content.Context
import androidx.paging.*
import id.xxx.data.source.map.box.Address
import id.xxx.data.source.map.box.DataMapper.toListSearchEntity
import id.xxx.data.source.map.box.DataMapper.toSearchModel
import id.xxx.data.source.map.box.Result
import id.xxx.data.source.map.box.local.LocalDataSource
import id.xxx.data.source.map.box.local.entity.PlacesEntity
import id.xxx.data.source.map.box.remote.RemoteDataSource
import id.xxx.data.source.map.box.remote.response.PlacesResponse
import id.xxx.fake.test.domain.halper.ApiResponse
import id.xxx.fake.test.domain.halper.NetworkBoundResource
import id.xxx.fake.test.domain.halper.Resource
import id.xxx.fake.test.domain.search.model.SearchModel
import id.xxx.fake.test.domain.search.repository.IRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@FlowPreview
class SearchRepository(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) : IRepository<SearchModel> {

    override fun getPlaceWithPagingData(value: String): Flow<PagingData<SearchModel>> = Pager(
        config = PagingConfig(pageSize = 10, enablePlaceholders = false),
        remoteMediator = RemoteMediator(value, remote.apiService, local)
    ) { local.getPaging(value) }.flow.map {
        return@map if (value == "") PagingData.empty() else it.map { searchEntity ->
            searchEntity.toSearchModel()
        }
    }

    override fun getPlaces(value: String): Flow<Resource<List<SearchModel>>> =
        object : NetworkBoundResource<List<SearchModel>, PlacesResponse>() {
            override fun loadFromDB(): Flow<List<SearchModel>> =
                local.search(value).map {
                    it.map { data -> data.toSearchModel() }
                }

            override suspend fun createCall(): Flow<ApiResponse<PlacesResponse>> =
                remote.getPlaces(value)

            override suspend fun saveCallResult(data: PlacesResponse) {
                local.insert(*toListSearchEntity.map(data.features).toTypedArray())
            }
        }.asFlow()

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
                emit(Resource.Error(Throwable(result.message), searchEntity?.let { toSearchModel.map(it) }))
            }
        }
    }.flowOn(Dispatchers.IO)

}