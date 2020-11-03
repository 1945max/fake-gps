package id.xxx.fake.gps.ui.auth.sign

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.xxx.data.source.firebase.auth.Resource
import id.xxx.fake.gps.domain.auth.model.UserModel
import id.xxx.fake.gps.domain.auth.usecase.IAuthInteractor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SignViewModel(private val auth: IAuthInteractor) : ViewModel() {

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
}