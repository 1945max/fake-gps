package id.xxx.data.source.firebase.auth

sealed class Resource<out R> {
    data class Loading<out T>(val data: T? = null) : Resource<T>()
    data class Success<out T>(val data: T) : Resource<T>()
    object Empty : Resource<Nothing>()
    data class Error<out T>(val errorMessage: String?, val data: T? = null) : Resource<T>()
}