package id.xxx.auth.presentation.ui

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.base.binding.activity.BaseActivityWithNavigation
import com.base.binding.delegate.viewBinding
import id.xxx.auth.R
import id.xxx.auth.databinding.ActivityAuthBinding

class AuthActivity : BaseActivityWithNavigation<ActivityAuthBinding>() {

    override val binding by viewBinding { ActivityAuthBinding.inflate(it) }

    override fun getIdNavHost() = R.id.nav_host_auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navHostFragment.findNavController())
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || navHostFragment.findNavController().navigateUp()
    }
}