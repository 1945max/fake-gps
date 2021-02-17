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
        try {
            val apiResponse = service.fetchPlaces(query)
            database.insert(*toListSearchEntity.map(apiResponse.features).toTypedArray())

//            val repos = apiResponse.items
//            val endOfPaginationReached = repos.isEmpty()
//            repoDatabase.withTransaction {
//                // clear all tables in the database
//                if (loadType == LoadType.REFRESH) {
//                    repoDatabase.remoteKeysDao().clearRemoteKeys()
//                    repoDatabase.reposDao().clearRepos()
//                }
//                val prevKey = if (page == GITHUB_STARTING_PAGE_INDEX) null else page - 1
//                val nextKey = if (endOfPaginationReached) null else page + 1
//                val keys = repos.map {
//                    RemoteKeys(repoId = it.id, prevKey = prevKey, nextKey = nextKey)
//                }
//                repoDatabase.remoteKeysDao().insertAll(keys)
//                repoDatabase.reposDao().insertAll(repos)
//            }
            return MediatorResult.Success(endOfPaginationReached = true)
        } catch (exception: IOException) {
            exception.printStackTrace()
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            exception.printStackTrace()
            return MediatorResult.Error(exception)
        }
    }
}