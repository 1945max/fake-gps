package id.xxx.base.extention

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

inline fun AppCompatActivity.setResultAdnFinis(block: Intent.() -> Unit = {}) {
    val intent = Intent()
    block(intent)
    setResult(Activity.RESULT_OK, intent)
    finish()
}

inline fun <reified T : AppCompatActivity> Context.openActivity(block: Intent.() -> Unit = {}) {
    val intent = Intent(this, T::class.java)
    block(intent)
    startActivity(intent)
}

fun AppCompatActivity.disableDisplayHomeInNavGraph(navHostFragment: Fragment) {
    navHostFragment.findNavController()
        .addOnDestinationChangedListener { controller, destination, arguments ->
            supportActionBar?.setDisplayShowHomeEnabled(false)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
}