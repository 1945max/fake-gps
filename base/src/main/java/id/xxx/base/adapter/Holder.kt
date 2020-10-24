package id.xxx.base.adapter

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class Holder<ItemBinding : ViewDataBinding>(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    var binding = DataBindingUtil.bind<ItemBinding>(itemView)
}