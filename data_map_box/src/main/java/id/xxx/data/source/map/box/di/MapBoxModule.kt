package id.xxx.data.source.map.box.di

import androidx.paging.ExperimentalPagingApi
import id.xxx.data.source.map.box.BuildConfig
import id.xxx.data.source.map.box.local.LocalDataSource
import id.xxx.data.source.map.box.local.database.room.AppDatabase
import id.xxx.data.source.map.box.remote.RemoteDataSource
import id.xxx.data.source.map.box.remote.network.ApiConfig
import id.xxx.data.source.map.box.remote.network.ApiService
import id.xxx.data.source.map.box.repository.RepositoryImpl
import id.xxx.fake.test.domain.search.model.SearchModel
import id.xxx.fake.test.domain.search.repository.IRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.dsl.module

@FlowPreview
@ExperimentalCoroutinesApi
@ExperimentalPagingApi
object MapBoxModule {
    private val networkModule = module {
        single { ApiConfig.service(BuildConfig.BASE_URL, ApiService::class.java) }
    }

    private val databaseModule = module {
        factory { get<AppDatabase>().searchHistoryDao }

        single { AppDatabase.getInstance(get()) }
    }

    private val dataSource = module {
        single { RemoteDataSource(get()) }
        single { LocalDataSource(get()) }
    }

    private val searchRepositoryModule = module {
        single<IRepository<SearchModel>> { RepositoryImpl(get(), get()) }
    }

    val modules = listOf(
        networkModule, databaseModule, dataSource, searchRepositoryModule
    )
}