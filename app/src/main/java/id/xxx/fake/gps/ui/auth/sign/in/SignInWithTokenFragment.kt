package id.xxx.fake.gps.ui.auth.sign.`in`

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import id.xxx.base.BaseFragment
import id.xxx.fake.gps.R
import id.xxx.fake.gps.databinding.FragmentSignInWithTokenBinding
import id.xxx.fake.gps.domain.auth.model.Result
import id.xxx.fake.gps.ui.auth.sign.statAuth
import kotlinx.android.synthetic.main.fragment_sign_in_with_token.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class SignInWithTokenFragment : BaseFragment<FragmentSignInWithTokenBinding>() {

    private val viewModel: SignInWithTokenViewModel by viewModels()

    override val layoutFragment = R.layout.fragment_sign_in_with_token

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.validateToken(token).asLiveData().observe(viewLifecycleOwner, {
            token.error = if (it is Result.Error) it.errorMessage else null
            viewModel.put(SignInWithTokenViewModel.KEY_TOKEN, token.error == null)
        })

        viewModel.getInputStat()
            .observe(viewLifecycleOwner, { login.isEnabled = it })

        binding.setOnClick { handleClick(it) }

        viewModel.loginResult.observe(viewLifecycleOwner, { statAuth(it) })
    }

    private fun handleClick(it: View) {
        when (it.id) {
            R.id.login -> viewModel.login(token.text.toString())
        }
    }
}