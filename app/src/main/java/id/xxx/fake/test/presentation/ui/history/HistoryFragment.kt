package id.xxx.fake.test.presentation.ui.history

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.base.binding.adapter.HolderWithBinding
import com.base.binding.delegate.viewBinding
import com.base.extension.setResult
import id.xxx.fake.test.R
import id.xxx.fake.test.databinding.FragmentHistoryBinding
import id.xxx.fake.test.databinding.ItemHistoryBinding
import id.xxx.fake.test.domain.adapter.ItemSwipeLR
import org.koin.android.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment(R.layout.fragment_history) {

    private val binding by viewBinding<FragmentHistoryBinding>()

    private var isLoaded = false

    private lateinit var adapterPaging: Adapter

    private val viewModel: HistoryViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterPaging = Adapter { _, model ->
            setResult {
                putExtra("latitude", model.latitude)
                putExtra("longitude", model.longitude)
            }
        }
        binding.recyclerView.apply {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, 1))
            ItemSwipeLR {
                @Suppress("UNCHECKED_CAST")
                val holderWithBinding = (it as HolderWithBinding<ItemHistoryBinding>)
                holderWithBinding.binding.data?.apply { viewModel.delete(this) }
            }.attachToRecyclerView(this)
            adapter = this@HistoryFragment.adapterPaging
        }

        viewModel.data.observe(viewLifecycleOwner, { adapterPaging.submitData(lifecycle, it) })

        adapterPaging.addLoadStateListener { handleLoadStateListener(it) }
    }

    private fun handleLoadStateListener(loadState: CombinedLoadStates) {
        if (loadState.refresh is LoadState.Loading) {
            binding.loadingProgressBar.visibility = View.VISIBLE
            isLoaded = true
        } else {
            if (isLoaded) binding.loadingProgressBar.visibility = View.GONE
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
}