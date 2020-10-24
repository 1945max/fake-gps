package id.xxx.base.adapter

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

abstract class BaseAdapterWithPaging<Entity : Parcelable, ItemBinding : ViewDataBinding>(
    @LayoutRes
    private val layout_item: Int,
    diffUtil: DiffUtil.ItemCallback<Entity>,
    protected val onItemClick: ItemClicked<Entity>? = null
) : PagingDataAdapter<Entity, Holder<ItemBinding>>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder<ItemBinding> {
        return Holder(LayoutInflater.from(parent.context).inflate(layout_item, parent, false))
    }
}