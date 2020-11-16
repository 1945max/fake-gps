package id.xxx.data.source.firebase.firestore.history.di

import id.xxx.data.source.firebase.firestore.history.local.LocalDataSource
import id.xxx.data.source.firebase.firestore.history.remote.RemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
object HistoryModule {
    private val dataSource = module {
        single { LocalDataSource(get()) }
        single { RemoteDataSource() }
    }

    val modules = listOf(dataSource)
}