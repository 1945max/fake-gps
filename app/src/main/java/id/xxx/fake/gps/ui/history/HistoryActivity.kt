package id.xxx.fake.gps.ui.history

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import id.xxx.base.BaseActivityWithNavigation
import id.xxx.fake.gps.R
import id.xxx.fake.gps.databinding.ActivityHistoryBinding
import id.xxx.fake.gps.utils.generateInt
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : BaseActivityWithNavigation<ActivityHistoryBinding>() {

    override val layoutRes: Int = R.layout.activity_history

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(findNavController(R.id.nav_host_history))
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || nav_host_history.findNavController().navigateUp()
    }

    companion object {
        val REQUEST_CODE by lazy { generateInt() }
    }
}