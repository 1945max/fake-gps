package id.xxx.di.koin

import id.xxx.auth.data.di.AutDataSourceModule
import id.xxx.auth.domain.di.AuthDomainModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.module.Module

@ExperimentalCoroutinesApi
object AuthModule {

    val dataModule = mutableListOf<Module>().apply {
        addAll(AutDataSourceModule.modules)
    }.toList()

    val domainModule = mutableListOf<Module>().apply {
        addAll(dataModule)
        addAll(AuthDomainModule.modules)
    }.toList()

    val presentationModules = mutableListOf<Module>().apply {
        addAll(dataModule)
        addAll(domainModule)
        addAll(id.xxx.auth.presentation.di.AuthPresentationModule.modules)
    }.toList()
}