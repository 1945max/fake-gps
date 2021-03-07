package id.xxx.auth.presentation.ui.sign.`in`

import androidx.fragment.app.Fragment
import id.xxx.auth.R
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class SignInWithTokenFragment : Fragment(R.layout.fragment_sign_in_with_token) {

//    private val binding by viewBinding<FragmentSignInWithTokenBinding>()
//
//    private val viewModel: SignInWithTokenViewModel by viewModels()
//
//    private val token by lazy { binding.token }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        token.asFlow().map { tokenIsValid(it) }.asLiveData().observe(viewLifecycleOwner, {
//            token.error = if (it) "min length 10" else null
//            viewModel.put(SignInWithTokenViewModel.KEY_TOKEN, token.error == null)
//        })
//
//        viewModel.getInputStat()
//            .observe(viewLifecycleOwner, { binding.login.isEnabled = it })
//
//        binding.setOnClick { handleClick(it) }
//
////        viewModel.getLoginResult().observe(viewLifecycleOwner, { statAuth(it) })
//    }
//
//    private fun handleClick(it: View) {
//        when (it.id) {
//            R.id.login -> viewModel.login(token.text.toString())
//        }
//    }
}