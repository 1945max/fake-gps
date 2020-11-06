package id.xxx.fake.gps.ui.auth.sign.`in`

import android.os.Bundle
import android.view.View
import androidx.lifecycle.asLiveData
import id.xxx.base.BaseFragment
import id.xxx.base.extention.asFlow
import id.xxx.fake.gps.R
import id.xxx.fake.gps.databinding.FragmentSignInWithTokenBinding
import id.xxx.fake.gps.ui.auth.sign.SignViewModel
import id.xxx.fake.gps.ui.auth.sign.statAuth
import kotlinx.android.synthetic.main.fragment_sign_in_with_token.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import org.koin.android.ext.android.inject

class SignInWithTokenFragment : BaseFragment<FragmentSignInWithTokenBinding>() {

    private val viewModel: SignViewModel by inject()

    override val layoutFragment = R.layout.fragment_sign_in_with_token

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        token.asFlow().map { it.length > 5 }.asLiveData()
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