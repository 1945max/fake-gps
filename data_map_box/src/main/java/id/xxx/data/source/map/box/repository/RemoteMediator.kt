package id.xxx.data.source.map.box.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import id.xxx.data.source.map.box.local.LocalDataSource
import id.xxx.data.source.map.box.local.entity.PlacesEntity
import id.xxx.data.source.map.box.remote.network.ApiService
import id.xxx.data.source.map.box.utils.DataMapper.toListSearchEntity
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class RemoteMediator(
    private val query: String,
    private val service: ApiService,
    private val database: LocalDataSource
) : RemoteMediator<Int, PlacesEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PlacesEntity>
    ): MediatorResult {
        return try {
            val apiResponse = service.fetchPlaces(query)
            database.insert(*toListSearchEntity.map(apiResponse.features).toTypedArray())
            MediatorResult.Success(endOfPaginationReached = true)
        } catch (exception: IOException) {
            exception.printStackTrace()
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            exception.printStackTrace()
            MediatorResult.Error(exception)
        }
    }
}