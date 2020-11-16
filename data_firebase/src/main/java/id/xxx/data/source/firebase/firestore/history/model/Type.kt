package id.xxx.data.source.firebase.firestore.history.model

sealed class Type<out T> {
    data class Added<out T>(val data: T) : Type<T>()
    data class Modified<out T>(val data: T) : Type<T>()
    data class Removed<out T>(val data: T) : Type<T>()
}