package id.xxx.data.source.firebase.di

import id.xxx.data.source.firebase.firestore.di.FireStoreModules
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
object FirebaseModule {
    val modules = FireStoreModules.modules
}