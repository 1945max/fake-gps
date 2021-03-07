package id.xxx.auth.presentation.ui.sign.verify

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.base.binding.delegate.viewBinding
import com.base.extension.openActivityAndFinish
import id.xxx.auth.R
import id.xxx.auth.databinding.FragmentVerifyBinding
import id.xxx.auth.domain.usecase.IIntractor
import org.koin.android.ext.android.inject

class VerifyFragment : Fragment(R.layout.fragment_verify) {

    private val binding by viewBinding<FragmentVerifyBinding>()

    private val interactor by inject<IIntractor>()

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            interactor.signOut()
            findNavController().navigate(R.id.move_to_fragment_welcome)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                interactor.signOut();true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        interactor.sendEmailVerify().asLiveData().observe(viewLifecycleOwner, {
            if (it) {
                val message = "Please Check Your Mail And Open Link To Verify"
                makeText(requireContext(), message, LENGTH_SHORT).show()
            }
        })

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, onBackPressedCallback
        )

        binding.btnConfirmVerifyEmail.setOnClickListener {
            interactor.isEmailVerify().asLiveData().observe(viewLifecycleOwner, { isVerify ->
                if (isVerify) {
                    requireActivity().openActivityAndFinish("id.xxx.fake.test.presentation.ui.home.HomeActivity")
                } else {
                    makeText(requireContext(), "email not yet verify", LENGTH_SHORT).show()
                }
            })
        }
    }
}