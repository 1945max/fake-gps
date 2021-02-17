package id.xxx.fake.test.ui.history

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.base.binding.delegate.viewBinding
import com.base.extension.setResult
import id.xxx.base.adapter.Holder
import id.xxx.base.adapter.ItemClicked
import id.xxx.base.adapter.ItemSwipeLR
import id.xxx.fake.test.R
import id.xxx.fake.test.databinding.FragmentHistoryBinding
import id.xxx.fake.test.databinding.ItemHistoryBinding
import id.xxx.fake.test.domain.history.model.HistoryModel
import kotlinx.android.synthetic.main.fragment_history.*
import org.koin.android.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment(R.layout.fragment_history) {

    private val binding by viewBinding<FragmentHistoryBinding>()

    private var isLoaded = false

    private lateinit var adapterPaging: Adapter

    private val viewModel: HistoryViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterPaging = Adapter(R.layout.item_history, object : ItemClicked<HistoryModel> {
            override fun onItemClick(model: HistoryModel) = setResult {
                putExtra("latitude", model.latitude)
                putExtra("longitude", model.longitude)
            }
        })
        recycler_view.apply {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, 1))
            ItemSwipeLR(object : ItemSwipeLR.OnSwipedCallback {
                override fun onItemSwipedLR(holder: RecyclerView.ViewHolder, direction: Int) {
                    ((holder as Holder<*>).binding as ItemHistoryBinding)
                        .data?.apply { viewModel.delete(this) }
                }
            }).attachToRecyclerView(this)
            adapter = this@HistoryFragment.adapterPaging
        }

        viewModel.data.observe(viewLifecycleOwner, { adapterPaging.submitData(lifecycle, it) })

        adapterPaging.addLoadStateListener { handleLoadStateListener(it) }
    }

    private fun handleLoadStateListener(loadState: CombinedLoadStates) {
        if (loadState.refresh is LoadState.Loading) {
            loading_progress_bar.visibility = View.VISIBLE
            isLoaded = true
        } else {
            if (isLoaded) loading_progress_bar.visibility = View.GONE
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