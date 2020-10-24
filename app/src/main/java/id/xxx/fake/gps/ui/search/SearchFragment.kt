package id.xxx.fake.gps.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import id.xxx.base.BaseFragment
import id.xxx.base.adapter.ItemClicked
import id.xxx.fake.gps.R
import id.xxx.fake.gps.databinding.FragmentSearchBinding
import id.xxx.fake.gps.domain.search.model.SearchModel
import id.xxx.data.source.map.box.Resource
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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

        lifecycleScope.launch { viewModel.searchResult.collectLatest { stat(it) } }
    }


    private fun stat(liveData: MediatorLiveData<Resource<PagingData<SearchModel>>>) {
        liveData.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    adapter.submitData(lifecycle, it.data)
                }
                is Resource.Empty -> {
//                    adapter.listData.clear();adapter.notifyDataSetChanged()
                }
                is Resource.Error -> {
                    it.data?.apply { adapter.submitData(lifecycle, this) } ?: run {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    /* STAT LOADING */
                }
            }
        })
    }

    override fun onItemClick(model: SearchModel) {
        val intent = Intent()
            .putExtra("latitude", model.latitude)
            .putExtra("longitude", model.longitude)
        setResultAndFinish(intent)
    }
}