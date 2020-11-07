package id.xxx.fake.gps.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import id.xxx.base.extention.openActivity
import id.xxx.fake.gps.domain.auth.model.User
import id.xxx.fake.gps.domain.auth.usecase.IInteractor
import id.xxx.fake.gps.ui.MainActivity
import id.xxx.fake.gps.ui.auth.AuthActivity
import id.xxx.fake.gps.ui.auth.verify.VerifyEmailActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {

    private val interactor by inject<IInteractor>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            interactor.reload().collectLatest {
                when (val user = interactor.getUser()) {
                    is User.Exist ->
                        if (user.data.isEmailVerified)
                            openActivity<MainActivity>()
                        else
                            openActivity<VerifyEmailActivity>()
                    is User.Empty -> openActivity<AuthActivity>()
                }.apply { finish() }
            }
        }
    }
}