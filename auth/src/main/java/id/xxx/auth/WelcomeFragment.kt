package id.xxx.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.base.binding.delegate.viewBinding
import com.base.extension.openActivityAndFinis
import id.xxx.auth.databinding.FragmentWelcomeBinding
import id.xxx.fake.test.ui.home.HomeActivity

class WelcomeFragment : Fragment(R.layout.fragment_welcome) {

    private val binding by viewBinding<FragmentWelcomeBinding>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.setOnClick {
            when (it.id) {
                R.id.btn_sign_with_token -> {
                }
                R.id.btn_sign_with_email -> {
                }
                R.id.btn_sign_up -> {
                }
                R.id.tv_anonymous -> {
                    openActivityAndFinis<HomeActivity>()
                }
            }
        }
    }
}