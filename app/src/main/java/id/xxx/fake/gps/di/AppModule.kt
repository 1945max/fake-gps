package id.xxx.fake.gps.di

import androidx.paging.ExperimentalPagingApi
import id.xxx.base.utils.Executors
import id.xxx.fake.gps.data.di.DataModule
import id.xxx.fake.gps.domain.di.DomainModule
import org.koin.dsl.module

@ExperimentalPagingApi
object AppModule {
    private val executors = module { single { Executors() } }

    val modules = mutableListOf(executors).apply {
        addAll(DataModule.modules)
        addAll(DomainModule.modules)
        addAll(ViewModelModule.modules)
    }
}