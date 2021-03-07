package id.xxx.di.koin

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.module.Module

@ExperimentalCoroutinesApi
object AuthModule {

    val dataModule = mutableListOf<Module>().apply {
        addAll(id.xxx.auth.data.source.di.DataModule.modules)
    }.toList()

    val domainModule = mutableListOf<Module>().apply {
        addAll(dataModule)
        addAll(id.xxx.auth.domain.di.DomainModule.modules)
    }.toList()

    val presentationModules = mutableListOf<Module>().apply {
        addAll(dataModule)
        addAll(domainModule)
        addAll(id.xxx.auth.presentation.di.AuthPresentationModule.modules)
    }.toList()
}