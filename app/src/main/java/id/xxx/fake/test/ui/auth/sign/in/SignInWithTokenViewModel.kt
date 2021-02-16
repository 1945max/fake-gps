package id.xxx.fake.test.ui.auth.sign.`in`

import androidx.lifecycle.MutableLiveData
import id.xxx.fake.test.ui.auth.sign.BaseSignViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class SignInWithTokenViewModel : BaseSignViewModel() {

    companion object {
        const val KEY_TOKEN = "TOKEN"
    }

    override val fieldStats = mutableMapOf(KEY_TOKEN to false)

    override val inputStats = MutableLiveData(fieldStats)
}