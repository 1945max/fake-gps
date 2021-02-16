package id.xxx.fake.test.ui.auth.sign

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import id.xxx.base.extention.setResult
import id.xxx.fake.test.R
import id.xxx.fake.test.domain.halper.Resource
import id.xxx.fake.test.domain.auth.model.UserModel
import id.xxx.fake.test.ui.auth.AuthActivity
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
            Toast.makeText(context, resource.errorMessage?.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }
}