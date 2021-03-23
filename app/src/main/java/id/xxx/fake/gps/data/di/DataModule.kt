package id.xxx.fake.gps.data.di

import androidx.paging.ExperimentalPagingApi
import id.xxx.data.source.firebase.auth.di.AuthModule
import id.xxx.data.source.firebase.firestore.di.FireStoreModules
import id.xxx.fake.gps.data.local.di.LocalModule
import id.xxx.fake.gps.data.repository.HistoryRepository
import id.xxx.fake.gps.domain.history.model.HistoryModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.dsl.module
import id.xxx.fake.gps.domain.history.repository.IRepository as IHistoryRepository

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@FlowPreview
object DataModule {
    private val historyRepositoryModule = module {
        single<IHistoryRepository<HistoryModel>> { HistoryRepository(get()) }
    }

    val modules = mutableListOf(
            historyRepositoryModule
    ).apply {
        addAll(FireStoreModules.modules)
        addAll(AuthModule.modules)
        addAll(LocalModule.modules)
    }
}