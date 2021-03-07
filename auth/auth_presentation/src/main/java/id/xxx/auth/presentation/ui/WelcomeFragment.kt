package id.xxx.auth.presentation.ui

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.base.binding.delegate.viewBinding
import com.base.extension.openActivityAndFinish
import id.xxx.auth.R
import id.xxx.auth.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment(R.layout.fragment_welcome) {

    private val binding by viewBinding<FragmentWelcomeBinding>()

    private fun navigate(@IdRes id: Int) = findNavController().navigate(id)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.setOnClick {
            when (it.id) {
                R.id.btn_sign_with_token -> navigate(R.id.move_to_fragment_sign_with_token)
                R.id.btn_sign_with_email -> navigate(R.id.move_to_fragment_sign_with_email)
                R.id.btn_sign_up -> navigate(R.id.move_to_fragment_sign_up)
                R.id.tv_anonymous -> requireActivity().openActivityAndFinish("id.xxx.fake.test.presentation.ui.home.HomeActivity")
            }
        }
    }
}