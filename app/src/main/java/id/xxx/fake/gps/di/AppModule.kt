package id.xxx.fake.gps.di

import androidx.paging.ExperimentalPagingApi
import id.xxx.auth.data.di.AutDataModule
import id.xxx.auth.domain.di.AuthDomainModule
import id.xxx.auth.presentation.di.AuthPresentationModule
import id.xxx.fake.gps.history.di.HistoryComponent
import id.xxx.map.box.di.MapBoxModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.core.module.Module

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@FlowPreview
object AppModule {

    val modules = mutableListOf<Module>(
        AppDatabaseModule.module
    ).apply {
        addAll(HistoryComponent.component)

        /* Auth Module */
        addAll(AuthPresentationModule.modules)
        addAll(AuthDomainModule.modules)
        addAll(AutDataModule.modules)

        /* Map Box Module */
        addAll(MapBoxModule.modules)
    }.toList()
}