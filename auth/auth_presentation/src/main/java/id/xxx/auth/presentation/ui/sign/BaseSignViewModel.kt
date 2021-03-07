package id.xxx.auth.presentation.ui.sign

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.KoinComponent
import kotlin.collections.MutableMap
import kotlin.collections.set

@ExperimentalCoroutinesApi
abstract class BaseSignViewModel : ViewModel(), KoinComponent {

    protected abstract val fieldStats: MutableMap<String, Boolean>
    protected abstract val inputStats: MutableLiveData<MutableMap<String, Boolean>>

    fun getInputStat(): LiveData<Boolean> = inputStats.map { !it.containsValue(false) }

    fun put(key: String, value: Boolean) = if (fieldStats.containsKey(key)) {
        fieldStats[key] = value
        inputStats.postValue(fieldStats)
    } else {
        throw Error("Field In $key Not Found")
    }
}