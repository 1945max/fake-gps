package id.xxx.fake.gps.ui.auth.sign.up

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.lifecycle.asLiveData
import com.jakewharton.rxbinding2.widget.RxTextView
import id.xxx.base.BaseFragment
import id.xxx.fake.gps.R
import id.xxx.fake.gps.databinding.FragmentSignUpBinding
import id.xxx.fake.gps.domain.auth.usecase.IInteractor
import id.xxx.fake.gps.ui.auth.sign.statAuth
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.koin.android.ext.android.inject

class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {

    private val authRepository by inject<IInteractor>()

    override val layoutFragment = R.layout.fragment_sign_up

    @SuppressLint("CheckResult")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.setOnClick { handleClick(it) }

        val username = RxTextView.textChanges(username)
            .skipInitialValue().map { !Patterns.EMAIL_ADDRESS.matcher(it).matches() }.apply {
                subscribe { username.error = if (it) "email not valid" else null }
            }

        val password = RxTextView.textChanges(password)
            .skipInitialValue().map { it.length <= 6 }.apply {
                subscribe { password.error = if (it) "max length 6" else null }
            }

        Observable.combineLatest(username, password) { a: Boolean, b: Boolean -> !a && !b }
            .subscribe { btn_sign_up.isEnabled = it }
    }

    private fun handleClick(it: View) {
        when (it.id) {
            R.id.btn_sign_up -> authRepository
                .createUser("${username.text}", "${password.text}").asLiveData()
                .observe(viewLifecycleOwner, { resources -> statAuth(resources) })
        }
    }
}