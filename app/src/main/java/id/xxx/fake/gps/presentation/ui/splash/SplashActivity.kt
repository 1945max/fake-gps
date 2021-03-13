package id.xxx.fake.gps.presentation.ui.splash

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import id.xxx.auth.domain.usecase.IAuthIntractor
import id.xxx.auth.presentation.ui.AuthActivity
import id.xxx.auth.presentation.ui.AuthActivity.Companion.authData
import id.xxx.base.domain.model.get
import id.xxx.base.extension.hideSystemUI
import id.xxx.base.extension.openActivityAndFinish
import id.xxx.fake.gps.presentation.ui.home.HomeActivity
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {

    private val interactor by inject<IAuthIntractor>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        interactor.currentUser().asLiveData().observe(this) {
            it.get(
                blockError = { _, e ->
                    finish()
                    Log.i("TAG", "onCreate: ${e.printStackTrace()}")
                },
                blockEmpty = { finishActivityAndSignOut<AuthActivity>(false) },
                blockSuccess = { user ->
                    if (user.isEmailVerify) {
                        finishActivityAndSignOut<HomeActivity>(false)
                    } else {
                        finishActivityAndSignOut<AuthActivity>(true)
                    }
                }
            )
        }
    }

    private inline fun <reified T : Activity> finishActivityAndSignOut(isSignOut: Boolean) {
        if (isSignOut) interactor.signOut()
        openActivityAndFinish<T> { authData(HomeActivity::class) }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasFocus) hideSystemUI()
    }
}