package id.xxx.fake.test.domain.di

import org.koin.dsl.module
import id.xxx.fake.test.domain.history.usecase.IInteractor as IHistoryInteractor
import id.xxx.fake.test.domain.history.usecase.InteractorImpl as HistoryInteractor
import id.xxx.fake.test.domain.search.usecase.IInteractor as ISearchInteractor
import id.xxx.fake.test.domain.search.usecase.InteractorImpl as SearchInteractor

object AppDomainModule {
    private val useCaseSearchModule = module {
        single<ISearchInteractor> { SearchInteractor(get()) }
    }

    private val useCaseHistoryModule = module {
        single<IHistoryInteractor> { HistoryInteractor(get()) }
    }

    val modules = listOf(
        useCaseHistoryModule,
        useCaseSearchModule,
    )
}