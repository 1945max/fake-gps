package id.xxx.data.source.firebase.auth.remote

sealed class ApiResponse<out R> {
    object Loading : ApiResponse<Nothing>()
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val errorMessage: String) : ApiResponse<Nothing>()
}