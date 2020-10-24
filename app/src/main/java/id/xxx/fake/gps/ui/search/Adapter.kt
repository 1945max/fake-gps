package id.xxx.fake.gps.ui.search

import androidx.appcompat.widget.SearchView
import id.xxx.base.adapter.BaseAdapter
import id.xxx.base.adapter.Holder
import id.xxx.base.adapter.ItemClicked
import id.xxx.fake.gps.R
import id.xxx.fake.gps.databinding.ItemSearchBinding
import id.xxx.fake.gps.domain.search.model.SearchModel

class Adapter(
    itemLayout: Int, onItemClick: ItemClicked<SearchModel>
) : BaseAdapter<ItemSearchBinding, SearchModel>(itemLayout, onItemClick) {
    override fun onBindViewHolder(holder: Holder<ItemSearchBinding>, position: Int) {
        val data = listData[position]
        holder.binding?.data = data
        holder.binding?.call?.setOnClickListener {
            it.rootView.findViewById<SearchView>(R.id.search_view).setQuery(data.name, false)
        }

        onItemClick?.apply { holder.binding?.root?.setOnClickListener { onItemClick(data) } }
    }
}