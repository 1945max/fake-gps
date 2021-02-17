package id.xxx.data.source.map.box.remote

import id.xxx.data.source.map.box.remote.network.ApiService
import id.xxx.fake.test.domain.halper.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource constructor(val apiService: ApiService) {
    fun getPlaces(query: String) = flow {
        try {
            val response = apiService.fetchPlaces(query)
            emit(
                if (response.features.isNotEmpty()) ApiResponse.Success(response) else ApiResponse.Empty
            )
        } catch (e: Exception) {
            emit(ApiResponse.Error(e))
        }
    }.flowOn(Dispatchers.IO)
}
