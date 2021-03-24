package id.xxx.fake.gps.history.di

import id.xxx.fake.gps.history.database.HistoryDatabase
import org.koin.dsl.module

object HistoryDatabaseModule {

    val module = module {
        single { HistoryDatabase.getInstance(get()) }
        single { get<HistoryDatabase>().historyDao }
    }
}