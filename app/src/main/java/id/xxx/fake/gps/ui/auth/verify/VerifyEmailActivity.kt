package id.xxx.fake.gps.ui.auth.verify

import android.os.Bundle
import id.xxx.base.BaseActivityWithNavigation
import id.xxx.base.extention.openActivity
import id.xxx.fake.gps.R
import id.xxx.fake.gps.databinding.ActivityVerifyEmailBinding
import id.xxx.fake.gps.ui.auth.AuthActivity
import id.xxx.fake.gps.ui.splash.SplashActivity
import kotlinx.android.synthetic.main.activity_verify_email.*
import org.koin.android.ext.android.inject

class VerifyEmailActivity : BaseActivityWithNavigation<ActivityVerifyEmailBinding>() {

    private val viewModel by inject<VerifyViewModel>()

    override val layoutRes = R.layout.activity_verify_email

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        back_to_login.setOnClickListener {
            viewModel.signOut
            openActivity<AuthActivity> { finish() }
        }

        viewModel.verifyEmail.observe(this, {
            if (it) openActivity<SplashActivity> { finish() }
        })
    }

}