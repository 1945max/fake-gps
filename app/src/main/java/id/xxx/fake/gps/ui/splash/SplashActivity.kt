package id.xxx.fake.gps.ui.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import id.xxx.fake.gps.ui.MainActivity

class SplashActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, MainActivity::class.java))
            .apply { finish() }
    }
}