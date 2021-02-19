package id.xxx.fake.test.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import id.xxx.fake.test.domain.search.usecase.IInteractor
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
    private val iInteractor: IInteractor,
    application: Application
) : AndroidViewModel(application) {

    private val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)
    private val query = queryChannel.asFlow()
        .debounce(300)
        .distinctUntilChanged()
        .mapLatest { it }
        .asLiveData()

    val searchResult = query.switchMap {
        iInteractor.getPlaceWithPagingData(it).cachedIn(viewModelScope).asLiveData()
    }

    fun sendQuery(value: String) = viewModelScope.launch { queryChannel.send(value) }

    fun getAddress(value: String) =
        iInteractor.getAddress(getApplication(), value)
}