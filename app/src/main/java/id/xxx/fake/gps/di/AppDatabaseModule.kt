package id.xxx.fake.gps.di

import id.xxx.fake.gps.database.AppDatabase
import org.koin.dsl.module

object AppDatabaseModule {

    val module = module {
        single { AppDatabase.getInstance(get()) }
        single { get<AppDatabase>().historyDao }
    }
}