package id.xxx.base.extention

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

inline fun Fragment.setResult(block: Intent.() -> Unit = {}) {
    val intent = Intent()
    block(intent)
    requireActivity().setResult(Activity.RESULT_OK, intent)
    requireActivity().finish()
}

inline fun <reified T : AppCompatActivity> Fragment.openActivity(
        isFinish: Boolean = false,
        block: Intent.() -> Unit = {}
) {
    val intent = Intent(requireContext(), T::class.java)
    block(intent)
    startActivity(intent)

    if (isFinish) requireActivity().finish()
}