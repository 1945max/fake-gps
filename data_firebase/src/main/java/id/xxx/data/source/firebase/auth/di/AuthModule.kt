package id.xxx.data.source.firebase.auth.di

import id.xxx.data.source.firebase.auth.local.LocalDataSource
import id.xxx.data.source.firebase.auth.local.database.AppDatabase
import id.xxx.data.source.firebase.auth.remote.RemoteDataSource
import id.xxx.data.source.firebase.auth.repository.AuthRepositoryImpl
import id.xxx.fake.test.domain.auth.model.UserModel
import id.xxx.fake.test.domain.auth.repository.AuthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
object AuthModule {
    private val databaseModule = module {
        factory { get<AppDatabase>().authDao() }

        single { AppDatabase.getInstance(get()) }
    }

    private val dataSource = module {
        single { RemoteDataSource() }
        single { LocalDataSource(get()) }
    }

    private val authRepository = module {
        single<AuthRepository<UserModel>> { AuthRepositoryImpl(get()) }
    }

    val modules = listOf(
        databaseModule, dataSource, authRepository
    )
}