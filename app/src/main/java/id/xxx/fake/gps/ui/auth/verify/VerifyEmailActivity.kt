package id.xxx.fake.gps.ui.auth.verify

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import id.xxx.base.BaseActivityWithNavigation
import id.xxx.base.extention.openActivity
import id.xxx.fake.gps.R
import id.xxx.fake.gps.databinding.ActivityVerifyEmailBinding
import id.xxx.fake.gps.domain.auth.usecase.IInteractor
import id.xxx.fake.gps.ui.auth.AuthActivity
import id.xxx.fake.gps.ui.splash.SplashActivity
import kotlinx.android.synthetic.main.activity_verify_email.*
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.ext.android.inject

class VerifyEmailActivity : BaseActivityWithNavigation<ActivityVerifyEmailBinding>() {

    private val interactor by inject<IInteractor>()

    override val layoutRes = R.layout.activity_verify_email

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        back_to_login.setOnClickListener {
            interactor.signOut()
            openActivity<AuthActivity>().run { finish() }
        }

        interactor.verifyEmail(lifecycleScope).observe(this, {
            if (it) openActivity<SplashActivity>().run { finish() }
        })
    }
}