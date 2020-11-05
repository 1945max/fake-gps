package id.xxx.fake.gps.ui.auth.sign.`in`

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.inputmethod.EditorInfo
import com.jakewharton.rxbinding2.widget.RxTextView
import id.xxx.base.BaseFragment
import id.xxx.fake.gps.R
import id.xxx.fake.gps.databinding.FragmentSignInWithEmailBinding
import id.xxx.fake.gps.ui.auth.sign.SignViewModel
import id.xxx.fake.gps.ui.auth.sign.statAuth
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_sign_in_with_email.*
import org.koin.android.ext.android.inject

class SignInWithEmailFragment : BaseFragment<FragmentSignInWithEmailBinding>() {

    private val loginViewModel: SignViewModel by inject()

    override val layoutFragment = R.layout.fragment_sign_in_with_email

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel.loginResult.observe(viewLifecycleOwner, { statAuth(it) })
        binding.setOnClick { handleClick(it) }

        password.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) loginViewModel.login(
                "${username.text}", "$password.text"
            ); return@setOnEditorActionListener false
        }

        val username = RxTextView.textChanges(username)
            .skipInitialValue().map { !Patterns.EMAIL_ADDRESS.matcher(it).matches() }.apply {
                subscribe { username.error = if (it) "email not valid" else null }
            }

        val password = RxTextView.textChanges(password)
            .skipInitialValue().map { it.length <= 6 }.apply {
                subscribe { password.error = if (it) "max length 6" else null }
            }

        Observable.combineLatest(username, password) { a: Boolean, b: Boolean -> !a && !b }
            .subscribe { login.isEnabled = it }
    }

    private fun handleClick(view: View) {
        when (view.id) {
            R.id.login -> loginViewModel.login("${username.text}", "$password.text")
        }
    }
}