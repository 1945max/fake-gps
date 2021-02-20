package id.xxx.fake.test.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import com.base.binding.adapter.BaseAdapter
import com.base.binding.adapter.HolderWithBinding
import id.xxx.fake.test.R
import id.xxx.fake.test.databinding.ItemSearchBinding
import id.xxx.fake.test.domain.search.model.SearchModel

class AdapterWithPaging(
    private val onItemClick: (ItemSearchBinding, SearchModel) -> Unit = { _, _ -> }
) : BaseAdapter.WithPaging3AndViewHolder<SearchModel, ItemSearchBinding>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HolderWithBinding(
        ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: HolderWithBinding<ItemSearchBinding>, position: Int) {
        val data = getItem(position) ?: return
        val binding = holder.binding
        binding.data = data
        binding.call.setOnClickListener {
            it.rootView.findViewById<SearchView>(R.id.search_view).setQuery(data.name, false)
        }
        holder.binding.root.setOnClickListener {
            data.apply { onItemClick(holder.binding, this) }
        }
    }
}