package id.xxx.fake.test.presentation.ui.splash

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import com.base.extension.hideSystemUI
import com.base.extension.openActivityAndFinish
import id.xxx.auth.domain.usecase.IIntractor
import id.xxx.auth.presentation.ui.AuthActivity
import id.xxx.base.domain.model.get
import id.xxx.fake.test.presentation.ui.home.HomeActivity
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {

    private val interactor by inject<IIntractor>()

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
        openActivityAndFinish<T>()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasFocus) hideSystemUI()
    }
}