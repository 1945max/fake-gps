package id.xxx.fake.gps.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.xxx.fake.gps.domain.history.model.HistoryModel
import id.xxx.fake.gps.domain.history.usecase.IHistoryUseCase

class HistoryViewModel(private val repository: IHistoryUseCase) : ViewModel() {

    val data = repository.getPagingData(viewModelScope).asLiveData()

    val delete = { data: HistoryModel -> repository.delete(data) }
}