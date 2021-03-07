package id.xxx.auth.presentation.ui.sign.up

import androidx.lifecycle.MutableLiveData
import id.xxx.auth.domain.usecase.IIntractor
import id.xxx.auth.presentation.ui.sign.BaseSignViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class SignUpViewModel(
    private val interactorAuth: IIntractor
) : BaseSignViewModel() {

    companion object {
        const val KEY_NAME = "NAME"
        const val KEY_EMAIL = "EMAIL"
        const val KEY_PASSWORD = "PASSWORD"
    }

    override val fieldStats =
        mutableMapOf(KEY_NAME to false, KEY_EMAIL to false, KEY_PASSWORD to false)

    override val inputStats = MutableLiveData(fieldStats)

    fun createUser(username: String, password: String) =
        interactorAuth.createUser(username, password)
}