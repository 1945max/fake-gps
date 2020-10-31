package id.xxx.data.source.map.box.remote

import id.xxx.data.source.map.box.remote.network.ApiResponse
import id.xxx.data.source.map.box.remote.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource constructor(private val apiService: ApiService) {
    fun getPlaces(query: String) = flow {
        try {
            val response = apiService.fetchPlaces(query)
            emit(
                if (response.features.isNotEmpty()) ApiResponse.Success(response) else ApiResponse.Empty
            )
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)
}
