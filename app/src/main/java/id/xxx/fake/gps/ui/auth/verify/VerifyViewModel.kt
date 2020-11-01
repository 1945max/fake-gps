package id.xxx.fake.gps.ui.auth.verify

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.xxx.fake.gps.domain.auth.usecase.IAuthUseCase

class VerifyViewModel(a: IAuthUseCase) : ViewModel() {
    val verifyEmail = a.verifyEmail().asLiveData()
    val signOut = a.signOut()
}