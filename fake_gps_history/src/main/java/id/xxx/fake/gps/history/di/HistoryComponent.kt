package id.xxx.fake.gps.history.di

import androidx.paging.ExperimentalPagingApi
import id.xxx.fake.gps.history.data.di.HistoryDataModule
import id.xxx.fake.gps.history.domain.di.HistoryDomainModule
import id.xxx.fake.gps.history.presentation.di.HistoryPresentationModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.core.module.Module

@FlowPreview
@ExperimentalPagingApi
@ExperimentalCoroutinesApi
object HistoryComponent {

    val component = mutableListOf<Module>().apply {
        addAll(HistoryDataModule.modules)
        addAll(HistoryDomainModule.modules)
        addAll(HistoryPresentationModule.modules)
    }.toList()
}