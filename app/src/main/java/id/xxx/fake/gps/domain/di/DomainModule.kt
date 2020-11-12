package id.xxx.fake.gps.domain.di

import id.xxx.fake.gps.domain.auth.usecase.InteractorImpl
import org.koin.dsl.module
import id.xxx.fake.gps.domain.auth.usecase.IInteractor as IAuthInteractor
import id.xxx.fake.gps.domain.history.usecase.IInteractor as IHistoryInteractor
import id.xxx.fake.gps.domain.history.usecase.Interactor as HistoryInteractor
import id.xxx.fake.gps.domain.search.usecase.IInteractor as ISearchInteractor
import id.xxx.fake.gps.domain.search.usecase.Interactor as SearchInteractor

object DomainModule {
    private val useCaseSearchModule = module {
        single<ISearchInteractor> { SearchInteractor(get()) }
    }

    private val useCaseHistoryModule = module {
        single<IHistoryInteractor> { HistoryInteractor(get()) }
    }

    private val useCaseAuthModule = module {
        single<IAuthInteractor> { InteractorImpl(get()) }
    }

    val modules = listOf(
            useCaseHistoryModule,
            useCaseSearchModule,
            useCaseAuthModule
    )
}