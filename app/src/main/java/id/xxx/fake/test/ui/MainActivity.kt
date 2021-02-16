package id.xxx.fake.test.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.xxx.base.extention.openActivity
import id.xxx.fake.test.domain.auth.model.UserModel
import id.xxx.fake.test.ui.auth.AuthActivity
import id.xxx.fake.test.ui.home.HomeActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    companion object {
        private val AUTH_CODE by lazy { Random.nextInt(100, 1000) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        openActivity<HomeActivity>()
//        startActivityForResult<AuthActivity>(requestCode = AUTH_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AUTH_CODE) {
            if (resultCode == Activity.RESULT_OK && data?.getParcelableExtra<UserModel>(AuthActivity.AUTH_EXTRA) != null) {
                openActivity<HomeActivity>()
            }
        }; finish()
    }
}