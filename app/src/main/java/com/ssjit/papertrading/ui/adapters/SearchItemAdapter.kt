package com.ssjit.papertrading.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssjit.papertrading.data.models.search.Data
import com.ssjit.papertrading.databinding.SearchItemBinding

class SearchItemAdapter(
    private val onClick: (String?) -> Unit
) : RecyclerView.Adapter<SearchItemAdapter.SearchItemViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Data>(){
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean =
                oldItem.symbol == newItem.symbol

        override fun areContentsTheSame(
                oldItem: Data,
                newItem: Data
        ) = oldItem == newItem
    }

    private val differ = AsyncListDiffer(this,diffCallback)

    fun submitData(list: List<Data>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val binding = SearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SearchItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class SearchItemViewHolder(private val binding:SearchItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: Data?) {
            binding.apply {
                tvSymbol.text = currentItem?.symbol
                tvCompany.text = currentItem?.name

                root.setOnClickListener {
                    onClick(currentItem?.symbol)
                }

            }

        }
    }

}