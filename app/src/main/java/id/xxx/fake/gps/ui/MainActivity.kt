package id.xxx.fake.gps.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import id.xxx.base.BaseActivityWithNavigation
import id.xxx.fake.gps.R
import id.xxx.fake.gps.databinding.ActivityMainBinding
import id.xxx.fake.gps.map.Map
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivityWithNavigation<ActivityMainBinding>(),
    NavController.OnDestinationChangedListener {

    override val layoutRes: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionBarWithNavController(findNavController(R.id.nav_host_main).apply {
            addOnDestinationChangedListener(this@MainActivity)
        })
//        if nav_host_fragment == FragmentContainerView
//        nav_host_fragment.post { setupActionBarWithNavController(nav_host_fragment.findNavController()) }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Map.REQUEST_PERMISSION_LOCATION -> if (grantResults[0] == 0 && grantResults[1] == 0) recreate()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || nav_host_main.findNavController().navigateUp()
    }

    override fun onDestinationChanged(ctr: NavController, des: NavDestination, args: Bundle?) {
        supportActionBar?.let { if (des.id == R.id.home_fragment) it.hide() else it.show() }
    }
}