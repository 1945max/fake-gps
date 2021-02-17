package id.xxx.fake.test.ui.auth.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.base.binding.delegate.viewBinding
import id.xxx.fake.test.R
import id.xxx.fake.test.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment(R.layout.fragment_welcome) {

    private val binding by viewBinding<FragmentWelcomeBinding>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.setOnClick {
            when (it.id) {
                R.id.btn_sign_with_token -> findNavController().navigate(R.id.action_to_sign_with_token)
                R.id.btn_sign_with_email -> findNavController().navigate(R.id.action_to_sign_with_email)
                R.id.btn_sign_up -> findNavController().navigate(R.id.action_to_sign_up)
            }
        }
    }
}