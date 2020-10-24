package id.xxx.fake.gps.ui.search

import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DiffUtil
import id.xxx.base.adapter.BaseAdapterWithPaging
import id.xxx.base.adapter.Holder
import id.xxx.base.adapter.ItemClicked
import id.xxx.fake.gps.R
import id.xxx.fake.gps.databinding.ItemSearchBinding
import id.xxx.fake.gps.domain.search.model.SearchModel

class AdapterWithPaging(
    itemLayout: Int, onItemClick: ItemClicked<SearchModel>
) : BaseAdapterWithPaging<SearchModel, ItemSearchBinding>(
    itemLayout, diffCallback, onItemClick
) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<SearchModel>() {
            override fun areItemsTheSame(
                oldItem: SearchModel,
                newItem: SearchModel
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: SearchModel,
                newItem: SearchModel
            ): Boolean = oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: Holder<ItemSearchBinding>, position: Int) {
        val data = getItem(position)
        holder.binding?.data = data
        holder.binding?.call?.setOnClickListener {
            it.rootView.findViewById<SearchView>(R.id.search_view).setQuery(data?.name, false)
        }

        onItemClick?.apply { holder.binding?.root?.setOnClickListener { data?.apply { onItemClick(this) } } }
    }
}