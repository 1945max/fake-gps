package id.xxx.fake.gps.ui.auth.welcome

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import id.xxx.base.BaseFragment
import id.xxx.fake.gps.R
import id.xxx.fake.gps.databinding.FragmentWelcomeBinding

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>() {
    override val layoutFragment = R.layout.fragment_welcome

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