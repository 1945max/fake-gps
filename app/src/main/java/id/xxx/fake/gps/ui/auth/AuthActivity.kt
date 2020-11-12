package id.xxx.fake.gps.ui.auth

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import id.xxx.base.BaseActivityWithNavigation
import id.xxx.base.extention.setResult
import id.xxx.fake.gps.R
import id.xxx.fake.gps.databinding.ActivityAuthBinding
import id.xxx.fake.gps.domain.auth.model.User
import id.xxx.fake.gps.domain.auth.usecase.IInteractor
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class AuthActivity : BaseActivityWithNavigation<ActivityAuthBinding>() {

    private val interactor by inject<IInteractor>()

    override val layoutRes = R.layout.activity_auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)
        setupActionBarWithNavController(nav_host_auth.findNavController())

        lifecycleScope.launch {
            val data = interactor.getUser()
            if (data is User.Exist && data.data.isEmailVerified) setResult { putExtra("data", true) }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.auth_menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || nav_host_auth.findNavController().navigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.contact -> true
        R.id.about -> true
        else -> false
    }
}