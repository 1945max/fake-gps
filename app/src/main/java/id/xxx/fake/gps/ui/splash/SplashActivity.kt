package id.xxx.fake.gps.ui.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import id.xxx.base.extention.openActivity
import id.xxx.base.extention.startActivityForResult
import id.xxx.fake.gps.ui.MainActivity
import id.xxx.fake.gps.ui.auth.AuthActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    companion object {
        const val AUTH_CODE = 321
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            delay(100)
            startActivityForResult<AuthActivity>(requestCode = AUTH_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AUTH_CODE && resultCode == Activity.RESULT_OK) {
            if (data!!.getBooleanExtra("data", false)) openActivity<MainActivity>()
        }; finish()
    }
}