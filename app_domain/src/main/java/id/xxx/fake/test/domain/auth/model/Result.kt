package id.xxx.fake.test.domain.auth.model

sealed class Result<out R> {
    object Valid : Result<Nothing>()
    data class Error<out T>(val errorMessage: String) : Result<T>()
}