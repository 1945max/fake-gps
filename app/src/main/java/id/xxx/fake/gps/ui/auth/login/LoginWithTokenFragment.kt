package id.xxx.fake.gps.ui.auth.login

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding2.widget.RxTextView
import id.xxx.base.BaseFragment
import id.xxx.base.extention.openActivity
import id.xxx.data.source.firebase.auth.Resource
import id.xxx.fake.gps.R
import id.xxx.fake.gps.databinding.FragmentLoginWithTokenBinding
import id.xxx.fake.gps.ui.splash.SplashActivity
import kotlinx.android.synthetic.main.fragment_login_with_token.*
import org.koin.android.ext.android.inject

class LoginWithTokenFragment : BaseFragment<FragmentLoginWithTokenBinding>() {

    private val loginViewModel: LoginViewModel by inject()

    override val layoutFragment = R.layout.fragment_login_with_token

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        RxTextView.textChanges(token).skipInitialValue().map {
            login.isEnabled = it.length > 5
        }.subscribe()

        login.setOnClickListener { loginViewModel.login(token.text.toString()) }

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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.login_with_email -> {
                findNavController().navigate(R.id.action_to_with_email);true
            }
            R.id.sign_up -> {
                findNavController().navigate(R.id.action_to_sign_up);true
            }
            else -> false
        }
    }
}