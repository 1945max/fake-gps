package id.xxx.data.source.map.box.remote

import id.xxx.data.source.map.box.remote.network.ApiService
import id.xxx.fake.test.domain.halper.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource constructor(val apiService: ApiService) {
    fun getPlaces(query: String) = flow {
        try {
            val response = if (query.isBlank()) {
                ApiResponse.Empty
            } else {
                val placesResponse = apiService.fetchPlaces(query)
                if (placesResponse.features.isNotEmpty()) ApiResponse.Success(placesResponse) else ApiResponse.Empty
            }
            emit(response)
        } catch (e: Throwable) {
            emit(ApiResponse.Error(e))
        }
    }.flowOn(Dispatchers.IO)
}