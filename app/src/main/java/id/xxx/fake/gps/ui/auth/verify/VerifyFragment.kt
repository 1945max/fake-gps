package id.xxx.fake.gps.ui.auth.verify

import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import id.xxx.base.BaseFragment
import id.xxx.base.extention.setResult
import id.xxx.fake.gps.R
import id.xxx.fake.gps.databinding.FragmentVerifyBinding
import id.xxx.fake.gps.domain.auth.model.User
import id.xxx.fake.gps.domain.auth.usecase.IInteractor
import id.xxx.fake.gps.ui.auth.AuthActivity
import kotlinx.android.synthetic.main.fragment_verify.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class VerifyFragment : BaseFragment<FragmentVerifyBinding>() {

    private val interactor by inject<IInteractor>()

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            lifecycleScope.launch {
                interactor.signOut().run { findNavController().popBackStack() }
            }
        }
    }

    override val layoutFragment = R.layout.fragment_verify

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        interactor.sendEmailVerify()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)

        btn_confirm_verify_email.setOnClickListener {
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