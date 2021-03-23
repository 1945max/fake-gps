package id.xxx.fake.gps.domain.di

import org.koin.dsl.module
import id.xxx.fake.gps.domain.history.usecase.IInteractor as IHistoryInteractor
import id.xxx.fake.gps.domain.history.usecase.InteractorImpl as HistoryInteractor

object AppDomainModule {
    private val useCaseHistoryModule = module {
        single<IHistoryInteractor> { HistoryInteractor(get()) }
    }

    val modules = listOf(
        useCaseHistoryModule,
    )
}