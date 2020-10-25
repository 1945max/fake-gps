package id.xxx.fake.gps.domain.di

import id.xxx.fake.gps.domain.history.usecase.HistoryInteractor
import id.xxx.fake.gps.domain.history.usecase.IHistoryUseCase
import id.xxx.fake.gps.domain.search.usecase.ISearchUseCase
import id.xxx.fake.gps.domain.search.usecase.SearchInteractor
import org.koin.dsl.module

object DomainModule {
    private val useCaseSearchModule = module {
        single<ISearchUseCase> { SearchInteractor(get()) }
    }

    private val useCaseHistoryModule = module {
        single<IHistoryUseCase> { HistoryInteractor(get()) }
    }

    val modules = listOf(
        useCaseHistoryModule, useCaseSearchModule
    )
}