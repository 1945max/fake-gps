package id.xxx.fake.test.di

import androidx.paging.ExperimentalPagingApi
import id.xxx.di.koin.AuthModule
import id.xxx.fake.test.data.di.DataModule
import id.xxx.fake.test.domain.di.AppDomainModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.core.module.Module

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@FlowPreview
object AppModule {
    val modules = mutableListOf<Module>().apply {
        addAll(DataModule.modules)
        addAll(ViewModelModule.modules)
        addAll(AppDomainModule.modules)
        addAll(AuthModule.presentationModules)
    }.toList()
}