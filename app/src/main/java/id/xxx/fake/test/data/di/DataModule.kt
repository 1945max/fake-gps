package id.xxx.fake.test.data.di

import androidx.paging.ExperimentalPagingApi
import id.xxx.data.source.firebase.auth.di.AuthModule
import id.xxx.data.source.firebase.firestore.di.FireStoreModules
import id.xxx.data.source.map.box.di.MapBoxModule
import id.xxx.fake.test.data.local.di.LocalModule
import id.xxx.fake.test.data.repository.HistoryRepository
import id.xxx.fake.test.domain.history.model.HistoryModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.dsl.module
import id.xxx.fake.test.domain.history.repository.IRepository as IHistoryRepository

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
        addAll(MapBoxModule.modules)
        addAll(FireStoreModules.modules)
        addAll(AuthModule.modules)
        addAll(LocalModule.modules)
    }
}