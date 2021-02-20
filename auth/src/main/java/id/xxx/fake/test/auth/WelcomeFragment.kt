package id.xxx.fake.test.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.base.binding.delegate.viewBinding
import id.xxx.fake.test.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment(R.layout.fragment_welcome) {

    private val binding by viewBinding<FragmentWelcomeBinding>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.setOnClick {

        }
    }
}