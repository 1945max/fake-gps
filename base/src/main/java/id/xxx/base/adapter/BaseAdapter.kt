package id.xxx.base.adapter

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import java.util.*

abstract class BaseAdapter<ItemBinding : ViewDataBinding, Model : Parcelable>(
    @LayoutRes
    private val layout_item: Int,
    val onItemClick: ItemClicked<Model>? = null
) : RecyclerView.Adapter<Holder<ItemBinding>>() {

    var listData = ArrayList<Model>()
        set(value) {
            field = value.apply { notifyDataSetChanged() }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder<ItemBinding> {
        return Holder(LayoutInflater.from(parent.context).inflate(layout_item, parent, false))
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return listData.size
    }
}