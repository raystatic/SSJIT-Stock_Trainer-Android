package com.ssjit.papertrading.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssjit.papertrading.data.models.FNO.Future
import com.ssjit.papertrading.data.models.transaction.Order
import com.ssjit.papertrading.databinding.ItemFutureBinding

class FutureItemAdapter: RecyclerView.Adapter<FutureItemAdapter.FutureItemViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<List<Future>>(){
        override fun areItemsTheSame(oldItem: List<Future>, newItem: List<Future>): Boolean =
                oldItem[0].expiryDate == newItem[0].expiryDate

        override fun areContentsTheSame(
                oldItem: List<Future>,
                newItem: List<Future>
        ) = oldItem == newItem
    }

    private val differ = AsyncListDiffer(this,diffCallback)

    fun submitData(list: List<List<Future>>) = differ.submitList(list)

    inner class FutureItemViewHolder(val binding:ItemFutureBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(future: Future?) {
            future?.let {
                binding.apply {
                    tvAnnualisedVolatility.text = it.annualisedVolatility
                    tvDate.text = it.expiryDate
                    tvDayVolatility.text = it.dailyVolatility
                    tvTotalSellQty.text = it.totalSellQuantity
                    tvTotalBuyQty.text = it.totalBuyQuantity
                    tvLowPrice.text = it.lowPrice
                    tvHighPrice.text = it.highPrice
                    tvOpenPrice.text = it.openPrice
                    tvLastPrice.text = it.lastPrice
                    tvLTP.text = it.ltp
                    tvpchange.text = "(${it.pChange}%)"

                    binding.lowHighSeekbar.setOnTouchListener { v, event ->
                        return@setOnTouchListener true
                    }

                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FutureItemViewHolder {
        val binding = ItemFutureBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return FutureItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FutureItemViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.bind(currentItem[0])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}