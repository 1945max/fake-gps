package id.xxx.fake.test.ui.auth.verify

import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.base.binding.delegate.viewBinding
import com.base.extension.setResult
import id.xxx.fake.test.R
import id.xxx.fake.test.databinding.FragmentVerifyBinding
import id.xxx.fake.test.domain.auth.model.User
import id.xxx.fake.test.domain.auth.usecase.IInteractor
import id.xxx.fake.test.ui.auth.AuthActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class VerifyFragment : Fragment(R.layout.fragment_verify) {

    private val binding by viewBinding<FragmentVerifyBinding>()

    private val interactor by inject<IInteractor>()

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            lifecycleScope.launch {
                interactor.signOut().run { findNavController().popBackStack() }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        interactor.sendEmailVerify()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)

        binding.btnConfirmVerifyEmail.setOnClickListener {
            lifecycleScope.launch {
                interactor.reload().collectLatest {
                    val user = interactor.getUser()
                    if (user is User.Exist)
                        if (user.data.isEmailVerified) {
                            setResult { putExtra(AuthActivity.AUTH_EXTRA, user.data) }
                        } else {
                            Toast.makeText(requireContext(), "please verify your email", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }
    }
}