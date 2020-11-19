package id.xxx.data.source.firebase.firestore.history.model

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
inline fun <ResultType, RequestType> networkBoundResourceFireStore(
    crossinline onFetchFailed: (Throwable) -> Unit = { Unit },
    crossinline loadFromDB: () -> Flow<ResultType>,
    crossinline shouldFetch: (ResultType) -> Boolean = { true },
    crossinline createCall: suspend () -> Flow<RequestType>,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
) = flow {

    val flowResultType = createCall()
        .map { saveFetchResult(it) }
        .flatMapLatest { loadFromDB() }

    emitAll(flowResultType)
}