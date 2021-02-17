package id.xxx.fake.test.ui.home

import androidx.navigation.fragment.findNavController
import com.base.binding.activity.BaseActivityWithNavigation
import com.base.binding.delegate.viewBinding
import id.xxx.fake.test.databinding.ActivityHomeBinding
import id.xxx.fake.test.ui.home.map.Map
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivityWithNavigation<ActivityHomeBinding>() {

    override val binding by viewBinding(ActivityHomeBinding::inflate)

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