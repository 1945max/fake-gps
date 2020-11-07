package id.xxx.fake.gps.ui.auth.sign.up

import androidx.lifecycle.MutableLiveData
import id.xxx.fake.gps.ui.auth.sign.BaseSignViewModel
import id.xxx.fake.gps.ui.auth.sign.`in`.SignInWithTokenViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class SignUpViewModel : BaseSignViewModel() {

    companion object {
        const val KEY_NAME = "NAME"
        const val KEY_EMAIL = "EMAIL"
        const val KEY_PASSWORD = "PASSWORD"
    }

    override val field = mutableMapOf(SignInWithTokenViewModel.KEY_TOKEN to false)

    override val inputStats = MutableLiveData(field)
}