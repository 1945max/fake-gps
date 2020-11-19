package id.xxx.fake.gps.di

import androidx.paging.ExperimentalPagingApi
import id.xxx.fake.gps.data.di.DataModule
import id.xxx.fake.gps.domain.di.DomainModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.core.module.Module

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@FlowPreview
object AppModule {
    val modules = mutableListOf<Module>().apply {
        addAll(DataModule.modules)
        addAll(DomainModule.modules)
        addAll(ViewModelModule.modules)
    }
}