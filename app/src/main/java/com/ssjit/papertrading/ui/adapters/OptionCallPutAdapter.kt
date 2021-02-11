package com.ssjit.papertrading.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssjit.papertrading.R
import com.ssjit.papertrading.data.models.FNO.Future
import com.ssjit.papertrading.data.models.FNO.MyOption
import com.ssjit.papertrading.data.models.FNO.Option
import com.ssjit.papertrading.databinding.ItemOptionCallsBinding

class OptionCallPutAdapter(var context:Context): RecyclerView.Adapter<OptionCallPutAdapter.OptionCallPutViewHolder>() {

    inner class OptionCallPutViewHolder(val itemOptionCallsBinding: ItemOptionCallsBinding):RecyclerView.ViewHolder(itemOptionCallsBinding.root){
        fun bind(item: MyOption,position: Int) {
            itemOptionCallsBinding.apply {
                tvCall.text = item.call
                tvPut.text = item.put
//                if (position == 0){
//                    linBackground.setBackgroundColor(ContextCompat.getColor(context, R.color.light_grey))
//                }else{
//                    if (position % 2 == 0)
//                        linBackground.setBackgroundColor(ContextCompat.getColor(context, R.color.light_grey))
//                    else
//                        linBackground.setBackgroundColor(ContextCompat.getColor(context, R.color.lightblue))
//                }


            }
        }

    }

    private val diffCallback = object : DiffUtil.ItemCallback<MyOption>(){
        override fun areItemsTheSame(oldItem: MyOption, newItem: MyOption): Boolean =
                oldItem == newItem

        override fun areContentsTheSame(
                oldItem: MyOption,
                newItem: MyOption
        ) = oldItem.hashCode() == newItem.hashCode()
    }

    private val differ = AsyncListDiffer(this,diffCallback)

    fun submitData(list: List<MyOption>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionCallPutViewHolder {
        val binding = ItemOptionCallsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return OptionCallPutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OptionCallPutViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item, position)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}