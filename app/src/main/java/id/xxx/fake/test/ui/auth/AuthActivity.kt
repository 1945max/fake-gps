package id.xxx.fake.test.ui.auth

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.base.binding.activity.BaseActivityWithNavigation
import com.base.binding.delegate.viewBinding
import com.base.extension.setResult
import id.xxx.fake.test.R
import id.xxx.fake.test.databinding.ActivityAuthBinding
import id.xxx.fake.test.domain.auth.model.User
import id.xxx.fake.test.domain.auth.usecase.IInteractor
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class AuthActivity : BaseActivityWithNavigation<ActivityAuthBinding>() {

    companion object {
        const val AUTH_EXTRA = "AUTH_EXTRA"
    }

    private val interactor by inject<IInteractor>()

    override val binding by viewBinding(ActivityAuthBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)
        setupActionBarWithNavController(nav_host_auth.findNavController())

        lifecycleScope.launch {
            val data = interactor.getUser()
            if (data is User.Exist) {
                if (data.data.isEmailVerified) setResult { putExtra(AUTH_EXTRA, data.data) } else {
                    nav_host_auth.findNavController().navigate(R.id.action_all_to_verify)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.auth_menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        if (nav_host_auth.findNavController().currentDestination?.id == R.id.verify)
            lifecycleScope.launch {
                interactor.signOut()
            }
        return super.onSupportNavigateUp() || nav_host_auth.findNavController().navigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.contact -> true
        R.id.about -> true
        else -> false
    }
}