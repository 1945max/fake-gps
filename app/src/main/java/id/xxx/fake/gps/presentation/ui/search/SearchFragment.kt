package id.xxx.fake.gps.presentation.ui.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import id.xxx.base.binding.delegate.viewBinding
import id.xxx.base.extension.setResult
import id.xxx.fake.gps.R
import id.xxx.fake.gps.databinding.FragmentSearchBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import org.koin.android.viewmodel.ext.android.sharedViewModel

@ExperimentalCoroutinesApi
@FlowPreview
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding<FragmentSearchBinding>()

    private lateinit var adapter: AdapterWithPaging

    private val viewModel by sharedViewModel<SearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = AdapterWithPaging { _, data ->
            setResult {
                putExtra("latitude", data.latitude)
                putExtra("longitude", data.longitude)
            }
        }
        binding.recyclerView.apply {
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
            binding.loadingProgressBar.visibility = View.VISIBLE
        } else {
            binding.loadingProgressBar.visibility = View.GONE
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
}