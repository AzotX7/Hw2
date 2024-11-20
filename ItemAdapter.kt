package com.example.homew1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homew1.databinding.ItemViewBinding

class ItemAdapter(private val clickListener: (Item) -> Unit, private val longClickListener: (Item) -> Unit) : ListAdapter<Item, ItemAdapter.ItemViewHolder>(ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener, longClickListener)


    }

    class ItemViewHolder(private val binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item, clickListener: (Item) -> Unit, longClickListener: (Item) -> Unit) {
            binding.textView.text = item.text
            binding.imageView.setImageResource(item.imageResId)
            binding.root.setOnClickListener { clickListener(item) }
            binding.root.setOnLongClickListener {
                longClickListener(item)
                true
            }
        }
    }

    class ItemDiffCallback : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.text == newItem.text && oldItem.imageResId == newItem.imageResId
        }
    }
}
