package id.xxx.data.source.fake.gps.di

import id.xxx.data.source.fake.gps.local.LocalDataSource
import id.xxx.data.source.fake.gps.local.database.room.AppDatabase
import org.koin.dsl.module

object FakeDataModule {
    private val databaseModule = module {
        factory { get<AppDatabase>().historyDao }

        single { AppDatabase.getInstance(get()) }
    }

    private val dataSource = module {
        single { LocalDataSource(get()) }
    }

    val modules = listOf(
        databaseModule, dataSource
    )
}