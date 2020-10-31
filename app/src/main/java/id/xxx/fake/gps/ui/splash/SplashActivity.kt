package id.xxx.fake.gps.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.xxx.fake.gps.data.repository.AuthRepository
import id.xxx.fake.gps.ui.MainActivity
import id.xxx.fake.gps.ui.auth.AuthActivity
import id.xxx.fake.gps.ui.auth.verify.VerifyEmailActivity
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {

    private val authRepository by inject<AuthRepository>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = authRepository.getUser()?.let {
            if (it.isEmailVerified) {
                Intent(this@SplashActivity, MainActivity::class.java)
            } else {
                Intent(this@SplashActivity, VerifyEmailActivity::class.java)
            }
        } ?: run { Intent(this@SplashActivity, AuthActivity::class.java) }

        startActivity(intent)
        finish()
    }
}