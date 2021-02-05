package com.ssjit.papertrading.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssjit.papertrading.data.models.search.Data
import com.ssjit.papertrading.data.models.stockdetail.StockData
import com.ssjit.papertrading.data.models.watchlist.Watchlist
import com.ssjit.papertrading.databinding.SearchItemBinding
import com.ssjit.papertrading.databinding.WatchlistItemBinding

class WatchlistAdapter(
    private val onClick: (String?) -> Unit
): RecyclerView.Adapter<WatchlistAdapter.WatchlistViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Watchlist>(){
        override fun areItemsTheSame(oldItem: Watchlist, newItem: Watchlist): Boolean =
            oldItem.data[0].symbol == newItem.data[0].symbol

        override fun areContentsTheSame(
            oldItem: Watchlist,
            newItem: Watchlist
        ) = oldItem == newItem
    }

    private val differ = AsyncListDiffer(this,diffCallback)

    fun submitData(list: List<Watchlist>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchlistViewHolder {
        val binding = WatchlistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return WatchlistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WatchlistViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.bind(currentItem.data[0])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class WatchlistViewHolder(private val binding: WatchlistItemBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: com.ssjit.papertrading.data.models.watchlist.Data?) {
            binding.apply {
                tvSymbol.text = currentItem?.symbol
                tvCompany.text = currentItem?.companyName
                tvPrice.text = "₹${currentItem?.lastPrice}"
                //tvChange.text = "$change"

                root.setOnClickListener {
                    onClick(currentItem?.symbol)
                }

            }

        }
    }

}