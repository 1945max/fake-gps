package id.xxx.fake.gps.ui.auth.verify

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import id.xxx.base.BaseActivityWithNavigation
import id.xxx.base.extention.openActivity
import id.xxx.fake.gps.R
import id.xxx.fake.gps.data.repository.AuthRepository
import id.xxx.fake.gps.databinding.ActivityVerifyEmailBinding
import id.xxx.fake.gps.ui.auth.AuthActivity
import id.xxx.fake.gps.ui.splash.SplashActivity
import kotlinx.android.synthetic.main.activity_verify_email.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class VerifyEmailActivity : BaseActivityWithNavigation<ActivityVerifyEmailBinding>() {
    private val authRepository by inject<AuthRepository>()

    override val layoutRes = R.layout.activity_verify_email

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        back_to_login.setOnClickListener {
            authRepository.signOut()
            openActivity<AuthActivity> { finish() }
        }

        lifecycleScope.launch {
            if (authRepository.verifyEmail()) openActivity<SplashActivity> { finish() }
        }
    }
}