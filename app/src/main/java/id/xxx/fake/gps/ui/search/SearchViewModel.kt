package id.xxx.fake.gps.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import id.xxx.fake.gps.domain.search.usecase.IInteractor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class SearchViewModel constructor(
    application: Application, private val iInteractor: IInteractor
) : AndroidViewModel(application) {

    private val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)
    val searchResult = queryChannel.asFlow()
        .debounce(300)
        .distinctUntilChanged()
        .mapLatest {
            iInteractor.getPlaceWithPagingData(it, viewModelScope).asLiveData()
        }.asLiveData()

    fun getAddress(value: String) = iInteractor.getAddress(getApplication(), value)

    fun sendQuery(value: String) = viewModelScope.launch { queryChannel.send(value) }
}