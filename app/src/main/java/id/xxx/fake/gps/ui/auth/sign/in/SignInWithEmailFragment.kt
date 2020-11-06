package id.xxx.fake.gps.ui.auth.sign.`in`

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.asLiveData
import id.xxx.base.BaseFragment
import id.xxx.fake.gps.R
import id.xxx.fake.gps.databinding.FragmentSignInWithEmailBinding
import id.xxx.fake.gps.domain.auth.model.Result
import id.xxx.fake.gps.ui.auth.sign.SignViewModel
import id.xxx.fake.gps.ui.auth.sign.statAuth
import kotlinx.android.synthetic.main.fragment_sign_in_with_email.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine
import org.koin.android.ext.android.inject

@ExperimentalCoroutinesApi
class SignInWithEmailFragment : BaseFragment<FragmentSignInWithEmailBinding>() {

    private val viewModel: SignViewModel by inject()

    override val layoutFragment = R.layout.fragment_sign_in_with_email

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loginResult.observe(viewLifecycleOwner, { statAuth(it) })

        binding.setOnClick { handleClick(it) }

        input_password.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE)
                viewModel.login("${input_email.text}", "${input_password.text}")
            return@setOnEditorActionListener false
        }

        val email = viewModel.validateEmail(input_email)
        email.asLiveData().observe(viewLifecycleOwner, {
            input_email.error = if (it is Result.Error) it.errorMessage else null
        })

        val password = viewModel.validatePassword(input_password)
        password.asLiveData().observe(viewLifecycleOwner, {
            input_password.error = if (it is Result.Error) it.errorMessage else null
        })

        email.combine(password) { a, b -> a == Result.Valid && b == Result.Valid }
            .asLiveData().observe(viewLifecycleOwner, { login.isEnabled = it })
    }

    private fun handleClick(view: View) {
        when (view.id) {
            R.id.login -> viewModel.login("${input_email.text}", "${input_password.text}")
        }
    }
}