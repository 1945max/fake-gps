package id.xxx.fake.gps.ui.auth.sign

import android.util.Patterns
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

class SignViewModel(private val auth: IInteractor) : ViewModel() {

    val loginResult = MediatorLiveData<Resource<UserModel>>()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            auth.sign(username, password).collect { loginResult.postValue(it) }
        }
    }

    fun login(token: String) {
        viewModelScope.launch {
            auth.sign(token).collect { loginResult.postValue(it) }
        }
    }

    @ExperimentalCoroutinesApi
    fun validateName(input: TextInputEditText) = input.asFlow().map {
        if (it.isBlank()) Result.Error("name not blank") else Result.Valid
    }

    @ExperimentalCoroutinesApi
    fun validateEmail(input: TextInputEditText) = input.asFlow().map {
        return@map if (!Patterns.EMAIL_ADDRESS.matcher(it).matches())
            Result.Error("email not valid") else Result.Valid
    }

    @ExperimentalCoroutinesApi
    fun validatePassword(input: TextInputEditText) = input.asFlow().map {
        return@map when {
            it.contains(" ") -> Result.Error("pass not contain space")
            it.length <= 5 -> Result.Error("min length 6")
            it.length > 10 -> Result.Error("max length 10")
            else -> Result.Valid
        }
    }
}