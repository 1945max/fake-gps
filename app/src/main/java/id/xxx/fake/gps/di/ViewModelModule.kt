package id.xxx.fake.gps.di

import id.xxx.fake.gps.ui.auth.sign.SignViewModel
import id.xxx.fake.gps.ui.auth.sign.up.SignUpViewModel
import id.xxx.fake.gps.ui.history.HistoryViewModel
import id.xxx.fake.gps.ui.search.SearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModule {
    private val viewModel = module {
        viewModel { HistoryViewModel(get()) }
        viewModel { SearchViewModel(get(), get()) }
        viewModel { SignViewModel(get()) }
        viewModel { SignUpViewModel() }
    }

    val modules = listOf(viewModel)
}