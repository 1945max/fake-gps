package id.xxx.base.adapter

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ItemSwipeLR(
    onSwipedCallback: OnSwipedCallback
) : ItemTouchHelper(ItemTouchCallback(onSwipedCallback)) {

    private class ItemTouchCallback(private val onSwipedCallback: OnSwipedCallback) :
        ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return makeMovementFlags(0, LEFT or RIGHT)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            onSwipedCallback.onItemSwipedLR(viewHolder, direction)
        }
    }

    interface OnSwipedCallback {
        fun onItemSwipedLR(holder: RecyclerView.ViewHolder, direction: Int)
    }
}