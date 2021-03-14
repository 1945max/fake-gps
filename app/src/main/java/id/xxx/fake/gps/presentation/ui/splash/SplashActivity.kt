package id.xxx.fake.gps.presentation.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import id.xxx.auth.domain.model.UserModel
import id.xxx.auth.domain.model.UserResource
import id.xxx.auth.domain.usecase.IAuthIntractor
import id.xxx.base.domain.model.Resource
import id.xxx.base.extension.hideSystemUI
import id.xxx.base.extension.openActivityAndFinish
import id.xxx.fake.gps.presentation.ui.home.HomeActivity
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {

    private val interactor by inject<IAuthIntractor>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        interactor.currentUser().asLiveData().observe(this) {
            if (it != Resource.Loading) {
                openActivityAndFinish<HomeActivity> {
                    val data =
                        if (it is Resource.Success) if (it.data is UserResource.Valid) (it.data as UserResource.Valid<UserModel>).data else null else null
                    putExtra(HomeActivity.DATA_EXTRA, data)
                }
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasFocus) hideSystemUI()
    }
}