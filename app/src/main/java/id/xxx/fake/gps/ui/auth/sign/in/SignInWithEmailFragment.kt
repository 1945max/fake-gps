package id.xxx.fake.gps.ui.auth.sign.`in`

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import id.xxx.base.BaseFragment
import id.xxx.fake.gps.R
import id.xxx.fake.gps.databinding.FragmentSignInWithEmailBinding
import id.xxx.fake.gps.domain.auth.model.Result
import id.xxx.fake.gps.ui.auth.sign.`in`.SignInWithEmailViewModel.Companion.KEY_EMAIL
import id.xxx.fake.gps.ui.auth.sign.`in`.SignInWithEmailViewModel.Companion.KEY_PASSWORD
import id.xxx.fake.gps.ui.auth.sign.statAuth
import kotlinx.android.synthetic.main.fragment_sign_in_with_email.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class SignInWithEmailFragment : BaseFragment<FragmentSignInWithEmailBinding>() {

    private val viewModel by viewModels<SignInWithEmailViewModel>()

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

        viewModel.validateEmail(input_email).asLiveData().observe(viewLifecycleOwner, {
            input_email.error = if (it is Result.Error) it.errorMessage else null
            viewModel.put(KEY_EMAIL, input_email.error == null)
        })

        viewModel.validatePassword(input_password).asLiveData().observe(viewLifecycleOwner, {
            input_password.error = if (it is Result.Error) it.errorMessage else null
            viewModel.put(KEY_PASSWORD, input_password.error == null)
        })

        viewModel.getInputStat()
            .observe(viewLifecycleOwner, { login.isEnabled = it })
    }

    private fun handleClick(view: View) {
        when (view.id) {
            R.id.login -> viewModel.login("${input_email.text}", "${input_password.text}")
        }
    }
}