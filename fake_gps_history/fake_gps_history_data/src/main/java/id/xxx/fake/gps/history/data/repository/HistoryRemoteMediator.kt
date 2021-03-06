package id.xxx.fake.gps.history.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import id.xxx.base.domain.model.ApiResponse
import id.xxx.fake.gps.history.data.source.local.entity.HistoryEntity
import id.xxx.fake.gps.history.data.source.remote.response.HistoryFireStoreResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

@ExperimentalPagingApi
class HistoryRemoteMediator(
    private val blockGetPage: suspend () -> Long?,
    private val blockRequest: suspend (Long?) -> Flow<ApiResponse<List<HistoryFireStoreResponse>>>,
    private val blockOnRequest: suspend (List<HistoryFireStoreResponse>) -> Boolean
) : BaseMediator<Long, HistoryFireStoreResponse, Int, HistoryEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, HistoryEntity>
    ): MediatorResult {

        return when (val apiResponse = request(page()).first()) {
            is ApiResponse.Success -> {
                blockOnRequest(apiResponse.data)
                MediatorResult.Success(false)
            }
            is ApiResponse.Error -> {
                MediatorResult.Error(apiResponse.error)
            }
            is ApiResponse.Empty -> {
                MediatorResult.Success(endOfPaginationReached = true)
            }
        }
    }

    override suspend fun page() = blockGetPage()

    override suspend fun request(page: Long?) = blockRequest(page)
}

@ExperimentalPagingApi
abstract class BaseMediator<PageType, ResponseType, Key : Any, Value : Any> :
    RemoteMediator<Key, Value>() {

    abstract suspend fun page(): PageType?

    abstract suspend fun request(page: PageType?): Flow<ApiResponse<List<ResponseType>>>
}