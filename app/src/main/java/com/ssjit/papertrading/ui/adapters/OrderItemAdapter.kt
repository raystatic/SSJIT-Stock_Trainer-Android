package com.ssjit.papertrading.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssjit.papertrading.R
import com.ssjit.papertrading.data.models.search.Data
import com.ssjit.papertrading.data.models.transaction.Order
import com.ssjit.papertrading.databinding.OrderItemBinding
import com.ssjit.papertrading.other.Constants
import com.ssjit.papertrading.other.Utility
import timber.log.Timber
import kotlin.math.roundToInt

class OrderItemAdapter(
        private val context:Context,
    private val onClick:(String) -> Unit
): RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder>() {

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

    inner class OrderItemViewHolder(val binding:OrderItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(order: Order) {
            binding.apply {
                tvType.text = order.type
                if (order.type == Constants.BUY){
                    tvType.setBackgroundColor(ContextCompat.getColor(context,R.color.primaryblue))
                }else{
                    tvType.setBackgroundColor(ContextCompat.getColor(context,R.color.yellow))
                }
                tvSymbol.text = order.symbol
                tvCompany.text = order.companyName
                Timber.d("${order.order_amount} ${order.no_shares}")
                val boughtPrice = (order.order_amount.toFloat() / order.no_shares.toInt()).toFloat()
                tvBoughtPrice.text = "Bought Price: $boughtPrice"
                tvQty.text = "QTY: ${order.no_shares} | LTP: ${order.currentPrice}"
                val changePerStock = order.currentPrice?.let { boughtPrice - it }
                val pointsChange = changePerStock?.let { it * order.no_shares.toInt() }
                tvChange.text = changePerStock.toString()

                if (pointsChange?.let { it >= 0 } == true){
                    tvChangePoints.text = "+${pointsChange.toString()}"
                    tvChangePoints.setTextColor(ContextCompat.getColor(context, R.color.green))
                }else{
                    tvChangePoints.text = "${pointsChange.toString()}"
                    tvChangePoints.setTextColor(ContextCompat.getColor(context, R.color.primary_red))
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder {
        val binding = OrderItemBinding.inflate(LayoutInflater.from(parent.context))

        return OrderItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int = differ.currentList.size
}