package id.xxx.fake.test.domain.auth.model

sealed class User<out R> {
    data class Exist<out T>(val data: T) : User<T>()
    object Empty : User<Nothing>()
}