package id.xxx.fake.gps.ui.search

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import id.xxx.base.BaseFragment
import id.xxx.base.adapter.ItemClicked
import id.xxx.base.extention.setResultAndFinish
import id.xxx.data.source.map.box.Resource
import id.xxx.fake.gps.R
import id.xxx.fake.gps.databinding.FragmentSearchBinding
import id.xxx.fake.gps.domain.search.model.SearchModel
import kotlinx.android.synthetic.main.fragment_history.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
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

        viewModel.searchResult.observe(viewLifecycleOwner, { stat(it) })
    }


    private fun stat(liveData: LiveData<Resource<PagingData<SearchModel>>>) {
        liveData.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> Log.i("TAG", "stat_loading: $it")
                is Resource.Success -> adapter.submitData(lifecycle, it.data)
                is Resource.Empty -> adapter.submitData(lifecycle, PagingData.empty())
                is Resource.Error -> {
                    it.data?.apply {
                        adapter.submitData(lifecycle, this)
                    } ?: run {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    override fun onItemClick(model: SearchModel) {
        setResultAndFinish {
            putExtra("latitude", model.latitude)
            putExtra("longitude", model.longitude)
        }
    }
}