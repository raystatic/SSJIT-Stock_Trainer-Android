package com.ssjit.papertrading.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssjit.papertrading.R
import com.ssjit.papertrading.data.models.search.Data
import com.ssjit.papertrading.data.models.transaction.Order
import com.ssjit.papertrading.databinding.OrderItemBinding
import com.ssjit.papertrading.other.Utility
import kotlin.math.roundToInt

class OrderItemAdapter(
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
                tvSymbol.text = order.symbol
                tvOrderAmount.text = Utility.formatAmount(order.order_amount)
                tvNoOfShares.text = order.no_shares
                val orderPrice:Int = (order.order_amount.replace(",","").toFloat() / order.no_shares.toInt()).roundToInt()
                tvOrderPrice.text = orderPrice.toString()
                tvOrderStatus.text = order.status
                root.setOnClickListener {
                    onClick(order.symbol)
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