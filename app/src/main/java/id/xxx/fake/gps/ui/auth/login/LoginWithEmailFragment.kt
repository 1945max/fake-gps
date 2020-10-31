package id.xxx.fake.gps.ui.auth.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Patterns
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding2.widget.RxTextView
import id.xxx.base.BaseFragment
import id.xxx.base.extention.openActivity
import id.xxx.data.source.firebase.auth.Resource
import id.xxx.fake.gps.R
import id.xxx.fake.gps.databinding.FragmentLoginWithEmailBinding
import id.xxx.fake.gps.ui.splash.SplashActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_login_with_email.*
import org.koin.android.ext.android.inject

class LoginWithEmailFragment : BaseFragment<FragmentLoginWithEmailBinding>() {

    private val loginViewModel: LoginViewModel by inject()

    override val layoutFragment = R.layout.fragment_login_with_email

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel.loginResult.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> loading.visibility = View.VISIBLE
                is Resource.Success -> openActivity<SplashActivity> { requireActivity().finish() }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                    loading.visibility = View.GONE
                }
                is Resource.Empty -> {
                    Toast.makeText(context, "Data Empty", Toast.LENGTH_SHORT).show()
                }
            }
        })

        password.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) loginViewModel.login(
                username.text.toString(), password.text.toString()
            ); return@setOnEditorActionListener false
        }

        login.setOnClickListener {
            loginViewModel.login(username.text.toString(), password.text.toString())
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.login_with_token -> {
                findNavController().navigate(R.id.action_to_with_token);true
            }
            R.id.sign_up -> {
                findNavController().navigate(R.id.action_to_sign_up);true
            }
            else -> false
        }
    }
}