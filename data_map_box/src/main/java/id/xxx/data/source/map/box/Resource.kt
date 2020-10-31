package id.xxx.data.source.map.box

sealed class Resource<out R> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error<out T>(val errorMessage: String?, val data: T? = null) : Resource<T>()
    object Loading : Resource<Nothing>()
    object Empty : Resource<Nothing>()
}