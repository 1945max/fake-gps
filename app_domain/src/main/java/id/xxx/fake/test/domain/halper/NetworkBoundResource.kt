package id.xxx.fake.test.domain.halper

import com.base.model.BaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline loadFromDB: () -> Flow<ResultType>,
    crossinline onFetchError: (Throwable) -> Unit = { },
    crossinline onFetchEmpty: (ResultType?) -> Unit = { },
    crossinline shouldFetch: (ResultType?) -> Boolean = { true },
    crossinline fetch: suspend () -> Flow<ApiResponse<RequestType>>,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline isValid: (ResultType?) -> Boolean = {
        when (it) {
            is BaseModel<*> -> it.isValid
            is List<*> -> it.isNotEmpty()
            else -> if (it == null) false else throw IllegalArgumentException("resultType not support")
        }
    },
) = flow {

    val resourceSuccessOrEmpty = { type: ResultType ->
        if (isValid(type)) Resource.Success(type) else Resource.Empty
    }

    val resultType = loadFromDB().firstOrNull()
    val firstResource = resultType
        ?.let { (if (isValid(it)) Resource.Success(it) else Resource.Loading) }
        ?: Resource.Loading
    emit(firstResource)

    val result =
        if (shouldFetch(resultType)) {
            when (val apiResponse = fetch().first()) {
                is ApiResponse.Success -> saveFetchResult(apiResponse.data).run {
                    loadFromDB().map { Resource.Success(it) }
                }
                is ApiResponse.Empty -> onFetchEmpty(resultType).run {
                    loadFromDB().map { resourceSuccessOrEmpty(it) }
                }
                is ApiResponse.Error -> onFetchError(apiResponse.error).run {
                    loadFromDB().map {
                        Resource.Error(apiResponse.error, if (isValid(it)) it else null)
                    }
                }
            }
        } else {
            loadFromDB().map { resourceSuccessOrEmpty(it) }
        }
    emitAll(result)
}.flowOn(Dispatchers.IO)