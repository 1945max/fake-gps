package id.xxx.fake.gps.ui.auth.sign.up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import kotlin.collections.set

class SignUpViewModel : ViewModel() {

    companion object {
        const val KEY_NAME = "NAME"
        const val KEY_EMAIL = "EMAIL"
        const val KEY_PASSWORD = "PASSWORD"
    }

    private val field = mutableMapOf(KEY_NAME to false, KEY_EMAIL to false, KEY_PASSWORD to false)

    private val inputStats = MutableLiveData(field)

    fun getInputStat(): LiveData<Boolean> = inputStats.map { !it.containsValue(false) }

    fun put(key: String, value: Boolean) = if (field.containsKey(key)) {
        field[key] = value
        inputStats.postValue(field)
    } else {
        throw Error("Field In $key Not Found")
    }
}