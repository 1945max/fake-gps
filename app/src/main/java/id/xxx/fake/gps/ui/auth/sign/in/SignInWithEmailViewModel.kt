package id.xxx.fake.gps.ui.auth.sign.`in`

import androidx.lifecycle.MutableLiveData
import id.xxx.fake.gps.ui.auth.sign.BaseSignViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class SignInWithEmailViewModel : BaseSignViewModel() {

    companion object {
        const val KEY_EMAIL = "EMAIL"
        const val KEY_PASSWORD = "PASSWORD"
    }

    override val fieldStats = mutableMapOf(KEY_EMAIL to false, KEY_PASSWORD to false)

    override val inputStats = MutableLiveData(fieldStats)
}