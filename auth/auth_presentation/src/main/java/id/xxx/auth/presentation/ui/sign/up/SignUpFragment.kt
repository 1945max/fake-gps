package id.xxx.auth.presentation.ui.sign.up

import android.os.Bundle
import android.view.View
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.base.binding.delegate.viewBinding
import id.xxx.auth.R
import id.xxx.auth.databinding.FragmentSignUpBinding
import id.xxx.auth.domain.model.InputValidation
import id.xxx.auth.domain.model.User
import id.xxx.auth.presentation.ui.sign.Utils
import id.xxx.auth.presentation.ui.sign.Utils.emailIsValid
import id.xxx.auth.presentation.ui.sign.Utils.nameIsValid
import id.xxx.base.domain.model.Resource
import id.xxx.base.domain.model.get
import id.xxx.base.extention.asFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import org.koin.android.ext.android.inject

@ExperimentalCoroutinesApi
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding<FragmentSignUpBinding>()

    private val viewModel by inject<SignUpViewModel>()

    private val inputEmail by lazy { binding.inputEmail }
    private val inputPassword by lazy { binding.inputPassword }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.setOnClick { handleClick(it) }

        val inputName = binding.inputName

        inputName.asFlow().map { nameIsValid(it) }.asLiveData().observe(viewLifecycleOwner, {
            inputName.error = if (it) null else "Field Name Not Valid"
            viewModel.put(SignUpViewModel.KEY_NAME, inputName.error == null)
        })

        inputEmail.asFlow().map { emailIsValid(it) }.asLiveData().observe(viewLifecycleOwner, {
            inputEmail.error = if (it) null else "Field Email Not Valid"
            viewModel.put(SignUpViewModel.KEY_EMAIL, inputEmail.error == null)
        })

        inputPassword.asFlow().map { Utils.passwordValidation(it) }.asLiveData()
            .observe(viewLifecycleOwner, {
                inputPassword.error = if (it is InputValidation.NotValid) it.message else null
                viewModel.put(SignUpViewModel.KEY_PASSWORD, inputPassword.error == null)
            })

        viewModel.getInputStat().observe(viewLifecycleOwner, { binding.btnSignUp.isEnabled = it })
    }

    private fun handleClick(it: View) {
        when (it.id) {
            R.id.btn_sign_up -> viewModel.createUser("${inputEmail.text}", "${inputPassword.text}")
                .asLiveData().observe(viewLifecycleOwner, this::statCreateUser)
        }
    }

    private fun statCreateUser(resource: Resource<User>) {
        resource.get(
            blockLoading = { showLoading(true) },
            blockSuccess = { user ->
                showLoading(false)
                if (user.loginMode == User.LoginMode.WITH_EMAIL) {
                    if (!user.isEmailVerify) {
                        findNavController().navigate(R.id.move_to_fragment_verify)
                    } else {
                        TODO("redirect to home")
                    }
                } else {
                    TODO("redirect to home")
                }
            },
            blockError = { _, error ->
                showLoading(false)
                makeText(requireContext(), error.localizedMessage, LENGTH_SHORT).show()
            }
        )
    }

    private fun showLoading(isShow: Boolean) {
        binding.btnSignUp.isEnabled = !isShow
        binding.loading.isVisible = isShow
    }
}