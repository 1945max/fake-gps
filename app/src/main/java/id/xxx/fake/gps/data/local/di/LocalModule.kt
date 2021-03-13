package id.xxx.fake.gps.data.local.di

import id.xxx.fake.gps.data.local.database.AppDatabase
import id.xxx.fake.gps.data.local.history.HistoryModule
import org.koin.dsl.module

object LocalModule {
    private val provideDatabase = module {
        single { AppDatabase.getInstance(get()) }
    }

    val modules = mutableListOf(provideDatabase).apply {
        addAll(HistoryModule.modules)
    }
}