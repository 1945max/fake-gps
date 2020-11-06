package id.xxx.fake.gps.ui.auth.sign.up

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.asLiveData
import id.xxx.base.BaseFragment
import id.xxx.fake.gps.R
import id.xxx.fake.gps.databinding.FragmentSignUpBinding
import id.xxx.fake.gps.domain.auth.model.Result
import id.xxx.fake.gps.domain.auth.usecase.IInteractor
import id.xxx.fake.gps.ui.auth.sign.SignViewModel
import id.xxx.fake.gps.ui.auth.sign.statAuth
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@FlowPreview
class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {

    private val signUpViewModel by viewModel<SignUpViewModel>()
    private val iInteractor by inject<IInteractor>()
    private val signViewModel by inject<SignViewModel>()

    override val layoutFragment = R.layout.fragment_sign_up

    @SuppressLint("CheckResult")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.setOnClick { handleClick(it) }

        val name = signViewModel.validateName(input_name)
        name.asLiveData().observe(viewLifecycleOwner, {
            input_name.error = if (it is Result.Error) it.errorMessage else null
            signUpViewModel.put(SignUpViewModel.KEY_NAME, input_name.error == null)
        })

        val email = signViewModel.validateEmail(input_email)
        email.asLiveData().observe(viewLifecycleOwner, {
            input_email.error = if (it is Result.Error) it.errorMessage else null
            signUpViewModel.put(SignUpViewModel.KEY_EMAIL, input_email.error == null)
        })

        val password = signViewModel.validatePassword(input_password)
        password.asLiveData().observe(viewLifecycleOwner, {
            input_password.error = if (it is Result.Error) it.errorMessage else null
            signUpViewModel.put(SignUpViewModel.KEY_PASSWORD, input_password.error == null)
        })

        signUpViewModel.getInputStat().observe(viewLifecycleOwner, { btn_sign_up.isEnabled = it })
    }

    private fun handleClick(it: View) {
        when (it.id) {
            R.id.btn_sign_up -> iInteractor
                .createUser("${input_email.text}", "${input_password.text}").asLiveData()
                .observe(viewLifecycleOwner, { resources -> statAuth(resources) })
        }
    }
}