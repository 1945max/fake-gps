package id.xxx.fake.gps.ui

import androidx.navigation.fragment.findNavController
import id.xxx.base.BaseActivityWithNavigation
import id.xxx.fake.gps.R
import id.xxx.fake.gps.databinding.ActivityMainBinding
import id.xxx.fake.gps.map.Map
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivityWithNavigation<ActivityMainBinding>() {

    override val layoutRes: Int = R.layout.activity_main

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
}