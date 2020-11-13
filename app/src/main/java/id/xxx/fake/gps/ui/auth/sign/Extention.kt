package id.xxx.fake.gps.ui.auth.sign

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import id.xxx.base.extention.setResult
import id.xxx.data.source.firebase.auth.Resource
import id.xxx.fake.gps.R
import id.xxx.fake.gps.domain.auth.model.UserModel
import id.xxx.fake.gps.ui.auth.AuthActivity
import kotlinx.android.synthetic.main.fragment_sign_up.*

fun Fragment.statAuth(resource: Resource<UserModel>) {
    when (resource) {
        is Resource.Success -> {
            val data = resource.data
            if (data.isEmailVerified) {
                setResult { putExtra(AuthActivity.AUTH_EXTRA, resource.data) }
            } else {
                findNavController().navigate(R.id.action_all_to_verify)
            }
        }
        is Resource.Loading -> loading.visibility = View.VISIBLE
        is Resource.Empty -> Toast.makeText(context, "Empty", Toast.LENGTH_SHORT).show()
        is Resource.Error -> {
            loading.visibility = View.GONE
            Toast.makeText(context, resource.errorMessage, Toast.LENGTH_SHORT).show()
        }
    }
}