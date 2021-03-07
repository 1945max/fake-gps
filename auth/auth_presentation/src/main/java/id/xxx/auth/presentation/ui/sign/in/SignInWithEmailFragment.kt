package id.xxx.auth.presentation.ui.sign.`in`

import android.os.Bundle
import android.view.View
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.base.binding.delegate.viewBinding
import com.base.extension.openActivityAndFinish
import id.xxx.auth.R
import id.xxx.auth.databinding.FragmentSignInWithEmailBinding
import id.xxx.auth.domain.model.InputValidation
import id.xxx.auth.presentation.ui.sign.Utils
import id.xxx.base.domain.model.get
import id.xxx.base.extention.asFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import org.koin.android.ext.android.inject

@ExperimentalCoroutinesApi
class SignInWithEmailFragment : Fragment(R.layout.fragment_sign_in_with_email) {

    private val binding by viewBinding<FragmentSignInWithEmailBinding>()

    private val viewModel by inject<SignInWithEmailViewModel>()

    private val inputEmail by lazy { binding.inputEmail }

    private val inputPassword by lazy { binding.inputPassword }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel.getLoginResult().observe(viewLifecycleOwner, { statAuth(it) })
//
//
//        inputPassword.setOnEditorActionListener { _, actionId, _ ->
//            if (actionId == EditorInfo.IME_ACTION_DONE)
//                viewModel.login("${inputEmail.text}", "${inputPassword.text}")
//            return@setOnEditorActionListener false
//        }
//
        binding.setOnClick { handleClick(it) }

        inputEmail.asFlow().map { Utils.emailIsValid(it) }.asLiveData()
            .observe(viewLifecycleOwner, {
                inputEmail.error = if (it) "Email Not Valid" else null
                viewModel.put(SignInWithEmailViewModel.KEY_EMAIL, inputEmail.error == null)
            })

        inputPassword.asFlow().map { Utils.passwordValidation(it) }.asLiveData()
            .observe(viewLifecycleOwner, {
                inputPassword.error = if (it is InputValidation.NotValid) it.message else null
                viewModel.put(SignInWithEmailViewModel.KEY_PASSWORD, inputPassword.error == null)
            })

        viewModel.getInputStat()
            .observe(viewLifecycleOwner, { binding.login.isEnabled = it })
    }

    private fun handleClick(view: View) {
        when (view.id) {
            R.id.login -> {
                viewModel.signIn("${inputEmail.text}", "${inputPassword.text}").asLiveData()
                    .observe(viewLifecycleOwner) {
                        it.get(
                            blockLoading = {
                                binding.loading.isVisible = true
                                binding.login.isEnabled = false
                            },
                            blockSuccess = {
                                requireActivity().openActivityAndFinish("id.xxx.fake.test.presentation.ui.home.HomeActivity")
                            },
                            blockError = { user, throwable ->
                                makeText(
                                    requireContext(),
                                    throwable.localizedMessage,
                                    LENGTH_SHORT
                                ).show()
                                binding.loading.isVisible = false
                                binding.login.isEnabled = true
                            },
                            blockEmpty = {
                                makeText(requireContext(), "User Not Found", LENGTH_SHORT).show()
                                binding.loading.isVisible = false
                                binding.login.isEnabled = true
                            }
                        )
                    }
            }
        }
    }
}