package id.xxx.fake.gps.data.di

import id.xxx.fake.gps.ui.history.HistoryViewModel
import id.xxx.fake.gps.ui.search.SearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModule {
    val viewModelModule = module {
        viewModel { HistoryViewModel(get()) }
        viewModel { SearchViewModel(get(), get()) }
    }
}