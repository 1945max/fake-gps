package id.xxx.data.source.firebase.auth.di

import id.xxx.data.source.firebase.auth.local.LocalDataSource
import id.xxx.data.source.firebase.auth.local.database.AppDatabase
import id.xxx.data.source.firebase.auth.remote.RemoteDataSource
import org.koin.dsl.module

object AuthModule {
    private val databaseModule = module {
        factory { get<AppDatabase>().authDao() }

        single { AppDatabase.getInstance(get()) }
    }

    private val dataSource = module {
        single { RemoteDataSource() }
        single { LocalDataSource(get()) }
    }

    val modules = listOf(
        databaseModule, dataSource
    )
}