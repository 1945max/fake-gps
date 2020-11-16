package id.xxx.data.source.firebase.auth

sealed class Resource<out R> {
    data class Success<out T>(val data: T) : Resource<T>()
    object Empty : Resource<Nothing>()
    object Loading : Resource<Nothing>()
    data class Error(val exception: Exception) : Resource<Nothing>()
}