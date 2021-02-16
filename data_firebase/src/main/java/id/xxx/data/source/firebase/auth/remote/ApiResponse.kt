package id.xxx.data.source.firebase.auth.remote

sealed class ApiResponse<out R> {
    object Empty : ApiResponse<Nothing>()
    object Loading : ApiResponse<Nothing>()
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val exception: Throwable) : ApiResponse<Nothing>()
}