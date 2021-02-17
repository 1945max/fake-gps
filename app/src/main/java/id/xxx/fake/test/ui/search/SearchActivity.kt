package id.xxx.fake.test.ui.search

import android.os.Bundle
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.base.binding.activity.BaseActivityWithNavigation
import com.base.binding.delegate.viewBinding
import com.base.extension.setResult
import id.xxx.fake.test.databinding.ActivitySearchBinding
import id.xxx.fake.test.domain.halper.Resource
import id.xxx.fake.test.domain.search.model.SearchModel
import id.xxx.fake.test.utils.generateInt
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@FlowPreview
class SearchActivity : BaseActivityWithNavigation<ActivitySearchBinding>(),
    SearchView.OnQueryTextListener {

    private val viewModel: SearchViewModel by viewModel()

    override val binding by viewBinding(ActivitySearchBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(nav_host_search.findNavController())
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
        search_view.setOnQueryTextListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        return super.onSupportNavigateUp() || nav_host_search.findNavController().navigateUp()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        lifecycleScope.launch {
            viewModel.getAddress(query ?: "").collect {
                resultOnQueryTextSubmit(it)
            }
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.sendQuery(newText ?: "")
        return true
    }

    private fun resultOnQueryTextSubmit(resource: Resource<SearchModel>) {
        when (resource) {
            is Resource.Loading -> makeText(baseContext, "loading", LENGTH_SHORT).show()
            is Resource.Empty -> makeText(baseContext, "data empty", LENGTH_SHORT).show()
            is Resource.Success -> resource.data.apply { setResultTextSubmit(latitude, longitude) }
            is Resource.Error -> {
                resource.data?.apply { setResultTextSubmit(latitude, longitude) } ?: run {
                    makeText(baseContext, resource.errorMessage?.localizedMessage, LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setResultTextSubmit(lat: Double, long: Double) = setResult {
        putExtra("latitude", lat)
        putExtra("longitude", long)
    }

    companion object {
        val REQUEST_CODE by lazy { generateInt() }
    }
}