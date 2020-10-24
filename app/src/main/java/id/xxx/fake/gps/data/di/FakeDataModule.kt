package id.xxx.fake.gps.data.di

import id.xxx.fake.gps.data.repository.HistoryRepository
import id.xxx.data.source.fake.gps.local.LocalDataSource
import id.xxx.data.source.fake.gps.local.database.room.AppDatabase
import id.xxx.fake.gps.domain.history.model.HistoryModel
import id.xxx.fake.gps.domain.history.repository.IHistoryRepository
import id.xxx.fake.gps.domain.history.usecase.HistoryInteractor
import id.xxx.fake.gps.domain.history.usecase.IHistoryUseCase
import org.koin.dsl.module

object FakeDataModule {
    val databaseModule = module {
        factory { get<AppDatabase>().historyDao }

        single { AppDatabase.getInstance(get()) }
    }

    val dataSource = module {
        single { LocalDataSource(get()) }
    }

    val repositoryModule = module {
        single<IHistoryRepository<HistoryModel>> { HistoryRepository(get(), get()) }
    }

    val useCaseModule = module {
        single<IHistoryUseCase> { HistoryInteractor(get()) }
    }
}