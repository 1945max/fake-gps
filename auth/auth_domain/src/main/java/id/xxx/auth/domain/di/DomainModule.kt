package id.xxx.auth.domain.di

import id.xxx.auth.domain.usecase.IIntractor
import id.xxx.auth.domain.usecase.InteractorImpl
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModule {
    private val useCase = module {
        single<IIntractor> { InteractorImpl(get()) }
    }

    val modules = mutableListOf(useCase)
}