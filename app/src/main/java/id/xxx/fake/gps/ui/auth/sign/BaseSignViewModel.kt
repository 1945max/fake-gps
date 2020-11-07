package id.xxx.fake.gps.ui.auth.sign

import android.util.Patterns
import androidx.lifecycle.*
import com.google.android.material.textfield.TextInputEditText
import id.xxx.base.extention.asFlow
import id.xxx.data.source.firebase.auth.Resource
import id.xxx.fake.gps.domain.auth.model.Result
import id.xxx.fake.gps.domain.auth.model.UserModel
import id.xxx.fake.gps.domain.auth.usecase.IInteractor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

@ExperimentalCoroutinesApi
abstract class BaseSignViewModel : ViewModel(), KoinComponent {

    private val iInteractor: IInteractor by inject()

    abstract val field: MutableMap<String, Boolean>
    abstract val inputStats: MutableLiveData<MutableMap<String, Boolean>>

    val loginResult = MediatorLiveData<Resource<UserModel>>()

    fun create(username: String, password: String) = iInteractor.createUser(username, password)

    fun login(username: String, password: String) {
        viewModelScope.launch {
            iInteractor.sign(username, password).collect { loginResult.postValue(it) }
        }
    }

    fun login(token: String) {
        viewModelScope.launch {
            iInteractor.sign(token).collect { loginResult.postValue(it) }
        }
    }

    fun validateName(input: TextInputEditText) = input.asFlow().map {
        if (it.isBlank()) Result.Error("name not blank") else Result.Valid
    }

    fun validateEmail(input: TextInputEditText) = input.asFlow().map {
        return@map if (!Patterns.EMAIL_ADDRESS.matcher(it).matches())
            Result.Error("email not valid") else Result.Valid
    }

    fun validatePassword(input: TextInputEditText) = input.asFlow().map {
        return@map when {
            it.contains(" ") -> Result.Error("pass not contain space")
            it.length <= 5 -> Result.Error("min length 6")
            it.length > 10 -> Result.Error("max length 10")
            else -> Result.Valid
        }
    }

    fun validateToken(input: TextInputEditText) = input.asFlow().map {
        if (it.length <= 10) Result.Error("min length 10") else Result.Valid
    }

    fun getInputStat(): LiveData<Boolean> = inputStats.map { !it.containsValue(false) }

    fun put(key: String, value: Boolean) = if (field.containsKey(key)) {
        field[key] = value
        inputStats.postValue(field)
    } else {
        throw Error("Field In $key Not Found")
    }
}