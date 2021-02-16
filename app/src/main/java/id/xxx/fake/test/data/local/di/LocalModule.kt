package id.xxx.fake.test.data.local.di

import id.xxx.fake.test.data.local.database.AppDatabase
import id.xxx.fake.test.data.local.history.HistoryModule
import org.koin.dsl.module

object LocalModule {
    private val provideDatabase = module {
        single { AppDatabase.getInstance(get()) }
    }

    val modules = mutableListOf(provideDatabase).apply {
        addAll(HistoryModule.modules)
    }
}