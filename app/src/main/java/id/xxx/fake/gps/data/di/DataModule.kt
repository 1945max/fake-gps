package id.xxx.fake.gps.data.di

import androidx.paging.ExperimentalPagingApi
import id.xxx.data.source.fake.gps.di.FakeDataModule
import id.xxx.data.source.map.box.di.MapBoxModule
import id.xxx.fake.gps.data.repository.AuthRepository
import id.xxx.fake.gps.data.repository.HistoryRepository
import id.xxx.fake.gps.data.repository.SearchRepository
import id.xxx.fake.gps.domain.auth.model.UserModel
import id.xxx.fake.gps.domain.history.model.HistoryModel
import id.xxx.fake.gps.domain.search.model.SearchModel
import id.xxx.fake.gps.domain.search.repository.IRepository
import org.koin.dsl.module
import id.xxx.fake.gps.domain.auth.repository.IRepository as IAuthRepository
import id.xxx.fake.gps.domain.history.repository.IRepository as IHistoryRepository

@ExperimentalPagingApi
object DataModule {
    private val searchRepositoryModule = module {
        single<IRepository<SearchModel>> { SearchRepository(get(), get()) }
    }

    private val historyRepositoryModule = module {
        single<IHistoryRepository<HistoryModel>> { HistoryRepository(get()) }
    }

    private val authRepository = module {
        single<IAuthRepository<UserModel>> { AuthRepository() }
    }

    val modules = mutableListOf(
        historyRepositoryModule, searchRepositoryModule, authRepository
    ).apply {
        addAll(MapBoxModule.modules)
        addAll(FakeDataModule.modules)
    }
}