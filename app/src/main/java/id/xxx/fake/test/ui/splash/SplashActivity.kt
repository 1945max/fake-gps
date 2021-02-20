package id.xxx.fake.test.ui.splash

import android.os.Bundle
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.base.extension.hideSystemUI
import com.base.extension.openActivityAndFinish
import com.base.extension.openDynamicFeature
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            delay(500)
            baseContext.openDynamicFeature(
                dynamicModuleName = "auth",
                onOpen = { openActivityAndFinish("id.xxx.fake.test.auth.AuthActivity") },
                onFailure = {
                    makeText(baseContext, "load module failure", LENGTH_SHORT).show()
                }
            )
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasFocus) hideSystemUI()
    }
}