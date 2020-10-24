package id.xxx.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.NavHostFragment

abstract class BaseActivityWithNavigation<ActivityBinding : ViewDataBinding> : AppCompatActivity() {
    protected abstract val layoutRes: Int
    protected lateinit var binding: ActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutRes)
    }

    /* Forward onActivityResult to fragment in NavHostFragment */
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode == Activity.RESULT_OK) {
            val navHostFragment = supportFragmentManager.fragments.first() as NavHostFragment
            val fragment = navHostFragment.childFragmentManager.fragments
            fragment.forEach { it.onActivityResult(requestCode, resultCode, intent) }
        }
    }

    /* Forward onOptionsItemSelected to fragment in NavHostFragment*/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navHostFragment = supportFragmentManager.fragments.first() as NavHostFragment
        val fragment = navHostFragment.childFragmentManager.fragments
        fragment.forEach { it.onOptionsItemSelected(item) }
        return super.onOptionsItemSelected(item)
    }

    protected fun setResultAndFinish(intent: Intent?) {
        this.setResult(Activity.RESULT_OK, intent)
        this.finish()
    }
}