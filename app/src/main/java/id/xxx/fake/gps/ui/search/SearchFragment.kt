package id.xxx.fake.gps.ui.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import id.xxx.base.BaseFragment
import id.xxx.base.adapter.ItemClicked
import id.xxx.base.extention.setResult
import id.xxx.fake.gps.R
import id.xxx.fake.gps.databinding.FragmentSearchBinding
import id.xxx.fake.gps.domain.search.model.SearchModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import org.koin.android.viewmodel.ext.android.sharedViewModel

@ExperimentalCoroutinesApi
@FlowPreview
class SearchFragment : BaseFragment<FragmentSearchBinding>(), ItemClicked<SearchModel> {

    private lateinit var adapter: AdapterWithPaging

    private val viewModel by sharedViewModel<SearchViewModel>()

    override val layoutFragment: Int = R.layout.fragment_search

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = AdapterWithPaging(R.layout.item_search, this)
        recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, 1))
            adapter = this@SearchFragment.adapter
        }

        viewModel.searchResult.observe(viewLifecycleOwner) { adapter.submitData(lifecycle, it) }
        adapter.addLoadStateListener { addLoadStateListener(it) }
        adapter.loadStateFlow
            .distinctUntilChangedBy { it.refresh }
            .filter { it.refresh is LoadState.NotLoading }
            .asLiveData().observe(viewLifecycleOwner, { binding.recyclerView.scrollToPosition(0) })
    }

    private fun addLoadStateListener(loadState: CombinedLoadStates) =
        if (loadState.refresh is LoadState.Loading) {
            loading_progress_bar.visibility = View.VISIBLE
        } else {
            loading_progress_bar.visibility = View.GONE
            val error = when {
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }
            error?.let {
                Toast.makeText(context, it.error.message, Toast.LENGTH_LONG).show()
            }
        }

    override fun onItemClick(model: SearchModel) {
        setResult {
            putExtra("latitude", model.latitude)
            putExtra("longitude", model.longitude)
        }
    }
}