package id.xxx.fake.test.ui.history

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import id.xxx.base.adapter.BaseAdapterWithPaging
import id.xxx.base.adapter.Holder
import id.xxx.base.adapter.ItemClicked
import id.xxx.fake.test.databinding.ItemHistoryBinding
import id.xxx.fake.test.domain.history.model.HistoryModel

class Adapter(
    itemLayout: Int, onItemClick: ItemClicked<HistoryModel>
) : BaseAdapterWithPaging<HistoryModel, ItemHistoryBinding>(itemLayout, diffCallback, onItemClick) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<HistoryModel>() {
            override fun areItemsTheSame(
                oldItem: HistoryModel,
                newItem: HistoryModel
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: HistoryModel,
                newItem: HistoryModel
            ): Boolean = oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: Holder<ItemHistoryBinding>, position: Int) {
        val data = getItem(position)
        holder.binding?.apply {
            this.data = data
            addressHistory.setOnLongClickListener {
                val clipboardManager =
                    it.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("text", (it as AppCompatTextView).text)
                clipboardManager.setPrimaryClip(clip)
                Toast.makeText(it.context, "copy", Toast.LENGTH_SHORT).show()
                return@setOnLongClickListener true
            }

            root.setOnClickListener { data?.let { onItemClick?.onItemClick(it) } }
        }
    }
}