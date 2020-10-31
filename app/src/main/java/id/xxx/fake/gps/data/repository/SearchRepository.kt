package id.xxx.fake.gps.data.repository

import android.content.Context
import androidx.paging.*
import id.xxx.data.source.map.box.NetworkBoundResource
import id.xxx.data.source.map.box.Resource
import id.xxx.data.source.map.box.local.LocalDataSource
import id.xxx.data.source.map.box.local.entity.PlacesEntity
import id.xxx.data.source.map.box.remote.RemoteDataSource
import id.xxx.data.source.map.box.remote.network.ApiResponse
import id.xxx.data.source.map.box.remote.response.PlacesResponse
import id.xxx.fake.gps.domain.search.model.SearchModel
import id.xxx.fake.gps.domain.search.repository.ISearchRepository
import id.xxx.fake.gps.utils.Address
import id.xxx.fake.gps.utils.DataMapper.toListSearchEntity
import id.xxx.fake.gps.utils.DataMapper.toSearchModel
import id.xxx.fake.gps.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class SearchRepository constructor(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) : ISearchRepository<SearchModel> {

    override fun getPagingData(
        value: String, scope: CoroutineScope
    ): Flow<Resource<PagingData<SearchModel>>> =
        object : NetworkBoundResource<PagingData<SearchModel>, PlacesResponse>() {
            override fun loadFromDB(): Flow<PagingData<SearchModel>> =
                Pager(config = PagingConfig(pageSize = 10, enablePlaceholders = false)) {
                    local.getPaging(value)
                }.flow.map {
                    it.map { searchEntity -> toSearchModel.map(searchEntity) }
                }.cachedIn(scope)

            override suspend fun createCall(): Flow<ApiResponse<PlacesResponse>> =
                remote.getPlaces(value)

            override suspend fun saveCallResult(data: PlacesResponse) {
                local.insert(*toListSearchEntity.map(data.features).toTypedArray())
            }
        }.asFlow()

    override fun getPlaces(value: String): Flow<Resource<List<SearchModel>>> =
        object : NetworkBoundResource<List<SearchModel>, PlacesResponse>() {
            override fun loadFromDB(): Flow<List<SearchModel>> =
                local.search(value).map {
                    it.map { searchEntity -> toSearchModel.map(searchEntity) }
                }

            override suspend fun createCall(): Flow<ApiResponse<PlacesResponse>> =
                remote.getPlaces(value)

            override suspend fun saveCallResult(data: PlacesResponse) {
                local.insert(*toListSearchEntity.map(data.features).toTypedArray())
            }
        }.asFlow()

//    @WorkerThread
//    override fun getAddress(context: Context, value: String): Resource<SearchModel> {
//        val isLatLong: Boolean = Address.isLatLong(value)
//        val data = ArrayList<Double>()
//        val latitude = 0
//        val longitude = 1
//        if (isLatLong) data.addAll(value.split(",").map { it.toDouble() })
//        return when (val result = Address.getInstance(context).getData(value)) {
//            is Result.Success -> {
//                val id = local.insert(
//                    PlacesEntity(
//                        name = if (isLatLong) result.data.getAddressLine(0) else value,
//                        latitude = if (isLatLong) data[latitude] else result.data.latitude,
//                        longitude = if (isLatLong) data[longitude] else result.data.longitude,
//                        address = result.data.getAddressLine(0),
//                    )
//                )
//
//                Resource.Success(toSearchModel.map(local.getByID(id.toInt())))
//            }
//            is Result.Empty -> Resource.Empty()
//            is Result.Error -> {
//                val searchEntity =
//                    if (isLatLong) local.select(data[0], data[1]) else local.select(value)
//                Resource.Error(result.message, searchEntity?.let { toSearchModel.map(it) })
//            }
//        }
//    }

    override fun getAddress(context: Context, value: String): Flow<Resource<SearchModel>> = flow {
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
                    if (isLatLong) local.select(data[0], data[1]) else local.select(value)
                emit(Resource.Error(result.message, searchEntity?.let { toSearchModel.map(it) }))
            }
        }
    }.flowOn(Dispatchers.IO)
}