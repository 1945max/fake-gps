package id.xxx.fake.test.di

import id.xxx.fake.test.ui.history.HistoryViewModel
import id.xxx.fake.test.ui.search.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
@FlowPreview
object ViewModelModule {
    private val viewModel = module {
        viewModel { HistoryViewModel(get()) }
        viewModel { SearchViewModel(get(), get()) }
    }

    val modules = listOf(viewModel)
}