package id.xxx.fake.gps.data.di

import id.xxx.fake.gps.data.repository.SearchRepository
import id.xxx.fake.gps.domain.search.model.SearchModel
import id.xxx.fake.gps.domain.search.repository.ISearchRepository
import id.xxx.fake.gps.domain.search.usecase.ISearchUseCase
import id.xxx.fake.gps.domain.search.usecase.SearchInteractor
import id.xxx.data.source.map.box.BuildConfig
import id.xxx.data.source.map.box.local.LocalDataSource
import id.xxx.data.source.map.box.local.database.room.AppDatabase
import id.xxx.data.source.map.box.remote.RemoteDataSource
import id.xxx.data.source.map.box.remote.network.ApiConfig
import id.xxx.data.source.map.box.remote.network.ApiService
import org.koin.dsl.module

object MapBoxModule {
    val networkModule = module {
        single { ApiConfig.service(BuildConfig.BASE_URL, ApiService::class.java) }
    }

    val databaseModule = module {
        factory { get<AppDatabase>().searchHistoryDao }

        single { AppDatabase.getInstance(get()) }
    }

    val dataSource = module {
        single { RemoteDataSource(get()) }
        single { LocalDataSource(get()) }
    }

    val repositoryModule = module {
        single<ISearchRepository<SearchModel>> { SearchRepository(get(), get()) }
    }

    val useCaseModule = module {
        single<ISearchUseCase> { SearchInteractor(get()) }
    }
}