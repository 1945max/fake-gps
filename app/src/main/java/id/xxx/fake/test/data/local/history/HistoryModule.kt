package id.xxx.fake.test.data.local.history

import id.xxx.fake.test.data.local.database.AppDatabase
import org.koin.dsl.module

object HistoryModule {
    private val dao = module {
        factory { get<AppDatabase>().historyDao }
    }

    private val localDataSource = module {
        single { LocalDataSource(get()) }
    }

    val modules = listOf(dao, localDataSource)
}