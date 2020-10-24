package id.xxx.data.source.map.box.remote

import id.xxx.data.source.map.box.remote.network.ApiResponse
import id.xxx.data.source.map.box.remote.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource constructor(private val apiService: ApiService) {
    fun getPlaces(query: String?) = flow {
        try {
            emit(ApiResponse.Success(apiService.fetchPlaces(query)))
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)
}
