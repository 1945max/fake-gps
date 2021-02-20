package id.xxx.fake.test.domain.halper

sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val error: Throwable) : ApiResponse<Nothing>()
    object Empty : ApiResponse<Nothing>()
}