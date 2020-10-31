package id.xxx.fake.gps.data.di

import id.xxx.data.source.fake.gps.di.FakeDataModule
import id.xxx.data.source.firebase.auth.di.AuthModule
import id.xxx.data.source.map.box.di.MapBoxModule
import id.xxx.fake.gps.data.repository.AuthRepository
import id.xxx.fake.gps.data.repository.HistoryRepository
import id.xxx.fake.gps.data.repository.SearchRepository
import id.xxx.fake.gps.domain.history.model.HistoryModel
import id.xxx.fake.gps.domain.history.repository.IHistoryRepository
import id.xxx.fake.gps.domain.search.model.SearchModel
import id.xxx.fake.gps.domain.search.repository.ISearchRepository
import org.koin.dsl.module

object DataModule {
    private val searchRepositoryModule = module {
        single<ISearchRepository<SearchModel>> { SearchRepository(get(), get()) }
    }

    private val historyRepositoryModule = module {
        single<IHistoryRepository<HistoryModel>> { HistoryRepository(get(), get()) }
    }

    private val authRepository = module {
        single { AuthRepository(get(), get()) }
    }

    val modules = mutableListOf(
        historyRepositoryModule, searchRepositoryModule, authRepository
    ).apply {
        addAll(MapBoxModule.modules)
        addAll(FakeDataModule.modules)
        addAll(AuthModule.modules)
    }
}