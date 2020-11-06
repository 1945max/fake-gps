package id.xxx.fake.gps.ui.auth.sign.up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@FlowPreview
class SignUpViewModel : ViewModel() {
    companion object {
        const val NAME = "NAME"
        const val EMAIL = "EMAIL"
        const val PASSWORD = "PASSWORD"
    }

    private val field = mutableMapOf(NAME to false, EMAIL to false, PASSWORD to false)
    private val queryChannel = BroadcastChannel<Map<String, Boolean>>(Channel.CONFLATED)

    val fieldStats = queryChannel.asFlow().asLiveData()

    fun put(key: String, value: Boolean) {
        field[key] = value
        viewModelScope.launch { queryChannel.send(field) }
    }
}