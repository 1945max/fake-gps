package id.xxx.fake.gps.di

import id.xxx.fake.gps.presentation.ui.history.HistoryViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
@FlowPreview
object ViewModelModule {
    private val viewModel = module {
        viewModel { HistoryViewModel(get()) }
    }

    val modules = listOf(viewModel)
}