package id.xxx.fake.gps.ui.auth.login

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import id.xxx.base.BaseFragment
import id.xxx.base.extention.openActivity
import id.xxx.data.source.firebase.auth.Resource
import id.xxx.fake.gps.R
import id.xxx.fake.gps.data.repository.AuthRepository
import id.xxx.fake.gps.databinding.FragmentSignUpBinding
import id.xxx.fake.gps.ui.splash.SplashActivity
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.koin.android.ext.android.inject

class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {

    private val authRepository by inject<AuthRepository>()

    override val layoutFragment = R.layout.fragment_sign_up

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        btn_sign_up.setOnClickListener {
            authRepository.createUser(
                email_address.text.toString(), password.text.toString()
            ).asLiveData().observeForever {
                when (it) {
                    is Resource.Success -> openActivity<SplashActivity> { requireActivity().finish() }
                    is Resource.Error -> Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    is Resource.Loading -> TODO()
                    is Resource.Empty -> TODO()
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.login_with_email -> {
                findNavController().navigate(R.id.action_to_with_email);true
            }
            R.id.login_with_token -> {
                findNavController().navigate(R.id.action_to_with_token);true
            }
            else -> false
        }
    }
}