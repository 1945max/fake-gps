package id.xxx.fake.gps.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import id.xxx.fake.gps.domain.search.model.SearchModel
import id.xxx.fake.gps.domain.search.usecase.ISearchUseCase
import id.xxx.data.source.map.box.Resource
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
    application: Application, private val search: ISearchUseCase
) : AndroidViewModel(application) {

    private val data = MediatorLiveData<Resource<PagingData<SearchModel>>>()

    private val queryChannel = BroadcastChannel<String?>(Channel.CONFLATED)
    val searchResult = queryChannel.asFlow()
        .debounce(300)
        .distinctUntilChanged()
        .mapLatest {
            return@mapLatest data.apply {
                if (it.isNullOrEmpty()) {
                    data.value = Resource.Empty()
//                } else data.addSource(search.getPlaces(it).asLiveData()) { resource ->
                } else data.addSource(
                    search.getPagingData(it, viewModelScope).asLiveData()
                ) { resource ->
                    data.value = resource
                }
            }
        }

    fun getAddress(value: String) = search.getAddress(getApplication(), value)

    fun sendQuery(value: String) = viewModelScope.launch { queryChannel.send(value) }
}