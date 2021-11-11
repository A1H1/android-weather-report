package com.demo.weatherreport.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseAdapter<T, U : ViewBinding>(private val coroutineScope: CoroutineScope) :
    RecyclerView.Adapter<BaseViewHolder<T, U>>() {
    protected var adapterListener: ((T) -> Unit)? = null
    protected val items: ArrayList<T> = ArrayList()

    fun setListener(listener: (T) -> Unit) {
        adapterListener = listener
    }

    override fun getItemCount() = items.size

    fun setItems(items: List<T>) {
        coroutineScope.launch {
            val oldList = this@BaseAdapter.items

            DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize() = oldList.size

                override fun getNewListSize() = items.size

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                    oldList[oldItemPosition] == items[newItemPosition]

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                    oldList[oldItemPosition]?.equals(items[newItemPosition]) ?: false
            }).dispatchUpdatesTo(this@BaseAdapter)

            this@BaseAdapter.items.clear()
            this@BaseAdapter.items.addAll(items)
        }
    }

    fun addItems(items: List<T>) {
        val startPosition = this.items.size
        this.items.addAll(items)
        notifyItemRangeInserted(startPosition, items.size)
    }

    fun addItem(item: T) {
        this.items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun addItem(item: T, position: Int) {
        this.items.add(position, item)
        notifyItemInserted(position)
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun updateItem(position: Int, item: T) {
        items[position] = item
        notifyItemChanged(position)
    }

    fun getItems(): List<T> = items

    override fun onBindViewHolder(holder: BaseViewHolder<T, U>, position: Int) {
        holder.bind(items[position])
    }
}

abstract class BaseViewHolder<T, U : ViewBinding>(binding: U) :
    RecyclerView.ViewHolder(binding.root) {
    open fun bind(data: T) {}
}