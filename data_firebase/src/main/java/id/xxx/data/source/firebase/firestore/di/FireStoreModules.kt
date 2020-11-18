package id.xxx.data.source.firebase.firestore.di

import id.xxx.data.source.firebase.firestore.database.AppDatabase
import id.xxx.data.source.firebase.firestore.history.di.HistoryModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
object FireStoreModules {
    private val databaseModule = module {
        single { AppDatabase.getInstance(get()) }

        factory { get<AppDatabase>().historyDao }

    }
    val modules = mutableListOf(
        databaseModule,
    ).apply {
        addAll(HistoryModules.modules)
    }
}