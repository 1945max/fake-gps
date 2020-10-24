package id.xxx.fake.gps.ui.history

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.xxx.base.BaseFragment
import id.xxx.base.adapter.Holder
import id.xxx.base.adapter.ItemClicked
import id.xxx.base.adapter.ItemSwipeLR
import id.xxx.data.source.fake.gps.Resource
import id.xxx.fake.gps.R
import id.xxx.fake.gps.databinding.FragmentHistoryBinding
import id.xxx.fake.gps.databinding.ItemHistoryBinding
import id.xxx.fake.gps.domain.history.model.HistoryModel
import kotlinx.android.synthetic.main.fragment_history.*
import org.koin.android.viewmodel.ext.android.viewModel

class HistoryFragment : BaseFragment<FragmentHistoryBinding>(),
    ItemClicked<HistoryModel>,
    ItemSwipeLR.OnSwipedCallback {

    private lateinit var adapterPaging: Adapter

    private val viewModel: HistoryViewModel by viewModel()

    override val layoutFragment: Int = R.layout.fragment_history

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterPaging = Adapter(R.layout.item_history, this)
        recycler_view.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, 1))
            adapter = this@HistoryFragment.adapterPaging
            ItemSwipeLR(this@HistoryFragment).attachToRecyclerView(this)
        }

        viewModel.data.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> {
                    /* Stat Loading */
                }
                is Resource.Success -> {
                    adapterPaging.submitData(lifecycle, it.data)
                }
                is Resource.Empty -> {

                }
                is Resource.Error -> {

                }
            }
        })
    }

    override fun onItemClick(model: HistoryModel) {
        requireActivity().apply {
            setResultAndFinish(
                Intent().putExtra("latitude", model.latitude).putExtra("longitude", model.longitude)
            )
        }
    }

    override fun onItemSwipedLR(holder: RecyclerView.ViewHolder, direction: Int) {
        ((holder as Holder<*>).binding as ItemHistoryBinding)
            .data?.apply { viewModel.delete(this) }
    }
}