package id.xxx.fake.gps.history.data.di

import androidx.paging.ExperimentalPagingApi
import id.xxx.fake.gps.history.data.local.LocalDataSource
import id.xxx.fake.gps.history.data.repository.HistoryRepository
import id.xxx.fake.gps.history.domain.model.HistoryModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.dsl.module
import id.xxx.fake.gps.history.domain.repository.IRepository as IHistoryRepository

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@FlowPreview
object HistoryDataModule {
    private val historyRepositoryModule = module {
        single<IHistoryRepository<HistoryModel>> { HistoryRepository(get()) }
    }

    private val localDataSource = module {
        single { LocalDataSource(get()) }
    }

    val modules = listOf(
        historyRepositoryModule, localDataSource
    )
}