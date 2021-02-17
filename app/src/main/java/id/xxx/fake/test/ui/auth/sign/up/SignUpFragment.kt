package id.xxx.fake.test.ui.auth.sign.up

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.base.binding.delegate.viewBinding
import id.xxx.fake.test.R
import id.xxx.fake.test.databinding.FragmentSignUpBinding
import id.xxx.fake.test.domain.auth.model.Result
import id.xxx.fake.test.ui.auth.sign.statAuth
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding<FragmentSignUpBinding>()


    private val viewModel by viewModels<SignUpViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.setOnClick { handleClick(it) }

        viewModel.validateName(input_name).asLiveData().observe(viewLifecycleOwner, {
            input_name.error = if (it is Result.Error) it.errorMessage else null
            viewModel.put(SignUpViewModel.KEY_NAME, input_name.error == null)
        })

        viewModel.validateEmail(input_email).asLiveData().observe(viewLifecycleOwner, {
            input_email.error = if (it is Result.Error) it.errorMessage else null
            viewModel.put(SignUpViewModel.KEY_EMAIL, input_email.error == null)
        })

        viewModel.validatePassword(input_password).asLiveData().observe(viewLifecycleOwner, {
            input_password.error = if (it is Result.Error) it.errorMessage else null
            viewModel.put(SignUpViewModel.KEY_PASSWORD, input_password.error == null)
        })

        viewModel.getInputStat().observe(viewLifecycleOwner, { btn_sign_up.isEnabled = it })
    }

    private fun handleClick(it: View) {
        when (it.id) {
            R.id.btn_sign_up -> viewModel.create(
                    "${input_email.text}", "${input_password.text}"
            ).asLiveData().observe(viewLifecycleOwner, { resources -> statAuth(resources) })
        }
    }
}