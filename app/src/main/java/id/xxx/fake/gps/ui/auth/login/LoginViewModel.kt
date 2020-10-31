package id.xxx.fake.gps.ui.auth.login

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.xxx.data.source.firebase.auth.Resource
import id.xxx.fake.gps.data.repository.AuthRepository
import id.xxx.fake.gps.domain.auth.model.UserModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel(private val auth: AuthRepository) : ViewModel() {

    val loginResult = MediatorLiveData<Resource<UserModel>>()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            auth.login(username, password).collect { loginResult.postValue(it) }
        }
    }

    fun login(token: String) {
        viewModelScope.launch {
            auth.login(token).collect { loginResult.postValue(it) }
        }
    }
}