package id.xxx.fake.test.domain.halper

sealed class Resource<out R> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error<out T>(val errorMessage: Throwable?, val data: T? = null) : Resource<T>()
    object Loading : Resource<Nothing>()
    object Empty : Resource<Nothing>()
}