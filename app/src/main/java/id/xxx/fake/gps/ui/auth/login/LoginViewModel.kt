package id.xxx.fake.gps.ui.auth.login

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.xxx.data.source.firebase.auth.Resource
import id.xxx.fake.gps.data.repository.AuthRepository
import id.xxx.fake.gps.domain.auth.model.UserModel
import kotlinx.coroutines.launch

class LoginViewModel(private val auth: AuthRepository) : ViewModel() {

    val loginResult = MediatorLiveData<Resource<UserModel>>()

    fun login(username: String, password: String) {
        loginResult.postValue(Resource.Loading())
        viewModelScope.launch {
            loginResult.postValue(auth.login(username, password))
        }
    }

    fun login(token: String) {
        loginResult.postValue(Resource.Loading())
        viewModelScope.launch {
            loginResult.postValue(auth.login(token))
        }
    }
}