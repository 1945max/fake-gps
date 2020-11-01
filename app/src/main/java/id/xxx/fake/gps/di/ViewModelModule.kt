package id.xxx.fake.gps.di

import id.xxx.fake.gps.ui.auth.login.LoginViewModel
import id.xxx.fake.gps.ui.auth.verify.VerifyViewModel
import id.xxx.fake.gps.ui.history.HistoryViewModel
import id.xxx.fake.gps.ui.search.SearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModule {
    private val viewModel = module {
        viewModel { HistoryViewModel(get()) }
        viewModel { SearchViewModel(get(), get()) }
        viewModel { LoginViewModel(get()) }
        viewModel { VerifyViewModel(get()) }
    }

    val modules = listOf(viewModel)
}