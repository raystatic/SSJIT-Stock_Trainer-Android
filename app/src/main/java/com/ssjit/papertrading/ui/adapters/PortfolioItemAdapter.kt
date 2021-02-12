package com.ssjit.papertrading.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssjit.papertrading.R
import com.ssjit.papertrading.data.models.orders.Order
import com.ssjit.papertrading.databinding.OrderItemBinding
import com.ssjit.papertrading.databinding.PortfolioItemBinding
import com.ssjit.papertrading.other.Constants
import com.ssjit.papertrading.other.Utility
import timber.log.Timber
import kotlin.math.roundToInt

class PortfolioItemAdapter(
        private val context:Context,
    private val onClick:(Order) -> Unit
): RecyclerView.Adapter<PortfolioItemAdapter.PortfolioItemViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Order>(){
        override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Order,
            newItem: Order
        ) = oldItem == newItem
    }

    private val differ = AsyncListDiffer(this,diffCallback)

    fun submitData(list: List<Order>) = differ.submitList(list)

    inner class PortfolioItemViewHolder(val binding:PortfolioItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(order: Order) {
            binding.apply {
                tvType.text = order.type
                if (order.type == Constants.BUY){
                    tvType.setBackgroundColor(ContextCompat.getColor(context,R.color.primaryblue))
                }else{
                    tvType.setBackgroundColor(ContextCompat.getColor(context,R.color.app_puple))
                }
                tvSymbol.text = order.symbol
                tvCompany.text = order.companyName
                val boughtPrice = order.eachPrice.toFloat()
                tvBoughtPrice.text = "Bought Price: $boughtPrice"
                tvQty.text = "QTY: ${order.noOfShares} | LTP: ${order.currentPrice}"
                val changePerStock = order.currentPrice?.let { boughtPrice - it.toFloat() }
                val pointsChange = changePerStock?.let { it * order.noOfShares.toInt() }
                tvChange.text = changePerStock.toString()

                if (pointsChange?.let { it >= 0 } == true){
                    tvChangePoints.text = "+${pointsChange.toString()}"
                    tvChangePoints.setTextColor(ContextCompat.getColor(context, R.color.green))
                }else{
                    tvChangePoints.text = "${pointsChange.toString()}"
                    tvChangePoints.setTextColor(ContextCompat.getColor(context, R.color.primary_red))
                }

                root.setOnClickListener {
                    onClick(order)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioItemViewHolder {
        val binding = PortfolioItemBinding.inflate(LayoutInflater.from(parent.context))

        return PortfolioItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PortfolioItemViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = differ.currentList.size
}