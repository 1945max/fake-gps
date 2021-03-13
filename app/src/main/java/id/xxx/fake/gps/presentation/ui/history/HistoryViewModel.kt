package id.xxx.fake.gps.presentation.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import id.xxx.fake.test.domain.history.model.HistoryModel
import id.xxx.fake.test.domain.history.usecase.IInteractor
import kotlinx.coroutines.launch

class HistoryViewModel(private val iInteractor: IInteractor) : ViewModel() {

    val data = iInteractor.getHistory().cachedIn(viewModelScope).asLiveData()

    val delete = { data: HistoryModel -> viewModelScope.launch { iInteractor.delete(data) } }
}