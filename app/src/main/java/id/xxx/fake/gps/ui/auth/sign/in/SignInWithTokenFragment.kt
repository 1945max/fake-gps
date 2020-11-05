package id.xxx.fake.gps.ui.auth.sign.`in`

import android.os.Bundle
import android.view.View
import com.jakewharton.rxbinding2.widget.RxTextView
import id.xxx.base.BaseFragment
import id.xxx.fake.gps.R
import id.xxx.fake.gps.databinding.FragmentSignInWithTokenBinding
import id.xxx.fake.gps.ui.auth.sign.SignViewModel
import id.xxx.fake.gps.ui.auth.sign.statAuth
import kotlinx.android.synthetic.main.fragment_sign_in_with_token.*
import org.koin.android.ext.android.inject

class SignInWithTokenFragment : BaseFragment<FragmentSignInWithTokenBinding>() {

    private val loginViewModel: SignViewModel by inject()

    override val layoutFragment = R.layout.fragment_sign_in_with_token

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        RxTextView.textChanges(token).skipInitialValue().map {
            login.isEnabled = it.length > 5
        }.subscribe()

        binding.setOnClick { handleClick(it) }

        loginViewModel.loginResult.observe(viewLifecycleOwner, { statAuth(it) })
    }

    private fun handleClick(it: View) {
        when (it.id) {
            R.id.login -> loginViewModel.login(token.text.toString())
        }
    }
}