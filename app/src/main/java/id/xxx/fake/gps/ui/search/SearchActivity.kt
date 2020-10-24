package id.xxx.fake.gps.ui.search

import android.content.Intent
import android.os.Bundle
import android.widget.Toast.LENGTH_SHORT
import android.widget.Toast.makeText
import androidx.appcompat.widget.SearchView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import id.xxx.base.BaseActivityWithNavigation
import id.xxx.base.utils.Executors
import id.xxx.fake.gps.R
import id.xxx.fake.gps.databinding.ActivitySearchBinding
import id.xxx.fake.gps.domain.search.model.SearchModel
import id.xxx.fake.gps.utils.generateInt
import id.xxx.data.source.map.box.Resource
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@FlowPreview
class SearchActivity : BaseActivityWithNavigation<ActivitySearchBinding>(),
    SearchView.OnQueryTextListener {

    private val executors: Executors by inject()

    private val viewModel: SearchViewModel by viewModel()

    override val layoutRes: Int = R.layout.activity_search

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(findNavController(R.id.nav_host_search))
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
        executors.networkIO().execute {
            resultOnQueryTextSubmit(viewModel.getAddress(query ?: ""))
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.sendQuery(newText ?: "")
        return true
    }

    private fun resultOnQueryTextSubmit(resource: Resource<SearchModel>) {
        val intent = { lat: Double, long: Double ->
            Intent().apply {
                putExtra("latitude", lat)
                putExtra("longitude", long)
            }
        }

        when (resource) {
            is Resource.Loading -> {
            }
            is Resource.Empty -> makeText(baseContext, "data empty", LENGTH_SHORT).show()
            is Resource.Success -> resource.data.apply {
                setResultAndFinish(intent(latitude, longitude))
            }
            is Resource.Error -> {
                executors.mainThread().execute {
                    makeText(baseContext, resource.errorMessage, LENGTH_SHORT).show()
                    resource.data?.apply { setResultAndFinish(intent(latitude, longitude)) }
                }
            }
        }
    }

    companion object {
        val REQUEST_CODE by lazy { generateInt() }
    }
}