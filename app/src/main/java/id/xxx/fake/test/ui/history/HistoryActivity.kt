package id.xxx.fake.test.ui.history

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.base.binding.activity.BaseActivityWithNavigation
import com.base.binding.delegate.viewBinding
import id.xxx.fake.test.R
import id.xxx.fake.test.databinding.ActivityHistoryBinding
import id.xxx.fake.test.utils.generateInt
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : BaseActivityWithNavigation<ActivityHistoryBinding>() {

    override val binding by viewBinding(ActivityHistoryBinding::inflate)

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