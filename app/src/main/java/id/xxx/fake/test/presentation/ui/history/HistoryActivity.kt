package id.xxx.fake.test.presentation.ui.history

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.base.binding.activity.BaseActivityWithNavigation
import com.base.binding.delegate.viewBinding
import id.xxx.fake.test.R.id.nav_host_history
import id.xxx.fake.test.databinding.ActivityHistoryBinding
import id.xxx.fake.test.utils.generateInt

class HistoryActivity : BaseActivityWithNavigation<ActivityHistoryBinding>() {

    override val binding by viewBinding(ActivityHistoryBinding::inflate)

    override fun getIdNavHost() = nav_host_history

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navHostFragment.findNavController())
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || navHostFragment.findNavController().navigateUp()
    }

    companion object {
        val REQUEST_CODE by lazy { generateInt() }
    }
}