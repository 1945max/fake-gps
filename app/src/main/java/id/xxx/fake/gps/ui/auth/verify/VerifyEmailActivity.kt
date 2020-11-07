package id.xxx.fake.gps.ui.auth.verify

import android.os.Bundle
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.lifecycle.lifecycleScope
import id.xxx.base.BaseActivityWithNavigation
import id.xxx.base.extention.openActivity
import id.xxx.fake.gps.R
import id.xxx.fake.gps.databinding.ActivityVerifyEmailBinding
import id.xxx.fake.gps.domain.auth.model.User
import id.xxx.fake.gps.domain.auth.usecase.IInteractor
import id.xxx.fake.gps.ui.auth.AuthActivity
import id.xxx.fake.gps.ui.splash.SplashActivity
import kotlinx.android.synthetic.main.activity_verify_email.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class VerifyEmailActivity : BaseActivityWithNavigation<ActivityVerifyEmailBinding>() {

    private val interactor by inject<IInteractor>()

    override val layoutRes = R.layout.activity_verify_email

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        interactor.sendEmailVerify()

        btn_confirm_verify_email.setOnClickListener {
            lifecycleScope.launch {
                interactor.reload().collectLatest {
                    val user = interactor.getUser()
                    if (user is User.Exist)
                        if (user.data.isEmailVerified) {
                            openActivity<SplashActivity>().run { finish() }
                        } else {
                            makeText(baseContext, "please verify your email", LENGTH_SHORT).show()
                        }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        interactor.signOut()
        openActivity<AuthActivity>().run { finish() }
        return super.onSupportNavigateUp()
    }
}