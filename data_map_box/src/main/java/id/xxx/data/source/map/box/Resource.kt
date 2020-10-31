package id.xxx.data.source.map.box

sealed class Resource<out R> {
    object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    object Empty : Resource<Nothing>()
    data class Error<out T>(val errorMessage: String?, val data: T? = null) : Resource<T>()
}