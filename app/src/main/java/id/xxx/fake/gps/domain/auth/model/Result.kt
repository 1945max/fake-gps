package id.xxx.fake.gps.domain.auth.model

sealed class Result<out R> {
    object Valid : Result<Nothing>()
    data class Error<out T>(val errorMessage: String) : Result<T>()
}