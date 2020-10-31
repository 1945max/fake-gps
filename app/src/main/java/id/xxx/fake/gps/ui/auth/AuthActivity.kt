package id.xxx.fake.gps.ui.auth

import android.os.Bundle
import android.view.Menu
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import id.xxx.base.BaseActivityWithNavigation
import id.xxx.fake.gps.R
import id.xxx.fake.gps.databinding.ActivityAuthBinding
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : BaseActivityWithNavigation<ActivityAuthBinding>() {

    override val layoutRes = R.layout.activity_auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)
        setupActionBarWithNavController(nav_host_auth.findNavController())
        nav_host_auth.findNavController().addOnDestinationChangedListener { _, _, _ ->
            supportActionBar?.setDisplayShowHomeEnabled(false)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.auth_menu, menu)
        return true
    }
}