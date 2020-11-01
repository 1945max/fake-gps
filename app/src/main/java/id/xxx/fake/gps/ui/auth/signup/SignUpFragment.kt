package id.xxx.fake.gps.ui.auth.signup

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import id.xxx.base.BaseFragment
import id.xxx.base.extention.openActivity
import id.xxx.data.source.firebase.auth.Resource.*
import id.xxx.fake.gps.R
import id.xxx.fake.gps.databinding.FragmentSignUpBinding
import id.xxx.fake.gps.domain.auth.usecase.IAuthUseCase
import id.xxx.fake.gps.ui.splash.SplashActivity
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {

    private val authRepository by inject<IAuthUseCase>()

    override val layoutFragment = R.layout.fragment_sign_up

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_sign_up.setOnClickListener {
            lifecycleScope.launch {
                authRepository.createUser(
                    email_address.text.toString(), password.text.toString()
                ).collect {
                    when (it) {
                        is Success -> openActivity<SplashActivity> { requireActivity().finish() }
                        is Error -> makeText(context, it.errorMessage, LENGTH_SHORT).show()
                        is Loading -> makeText(context, "Loading", LENGTH_SHORT).show()
                        is Empty -> makeText(context, "Empty", LENGTH_SHORT).show()
                    }
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