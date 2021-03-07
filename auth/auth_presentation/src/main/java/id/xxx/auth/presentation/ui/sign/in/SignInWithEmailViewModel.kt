package id.xxx.auth.presentation.ui.sign.`in`

import androidx.lifecycle.MutableLiveData
import id.xxx.auth.domain.usecase.IIntractor
import id.xxx.auth.presentation.ui.sign.BaseSignViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class SignInWithEmailViewModel(
    private val interactorAuth: IIntractor
) : BaseSignViewModel() {

    companion object {
        const val KEY_EMAIL = "EMAIL"
        const val KEY_PASSWORD = "PASSWORD"
    }

    override val fieldStats = mutableMapOf(KEY_EMAIL to false, KEY_PASSWORD to false)

    override val inputStats = MutableLiveData(fieldStats)

    fun signIn(userName: String, pass: String) = interactorAuth.signIn(userName,pass)
}