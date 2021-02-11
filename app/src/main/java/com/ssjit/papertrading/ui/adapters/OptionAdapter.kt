package com.ssjit.papertrading.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.ssjit.papertrading.data.models.FNO.MyDate
import com.ssjit.papertrading.data.models.FNO.MyOption
import com.ssjit.papertrading.databinding.ItemOptionBinding

class OptionAdapter(var context:Context):RecyclerView.Adapter<OptionAdapter.OptionViewHolder>() {

    inner class OptionViewHolder(val binding:ItemOptionBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(myDate: MyDate){
            binding.apply {
                tvDate.text = myDate.date
                val callPutAdapter = OptionCallPutAdapter(context)
                rvOption.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = callPutAdapter
                    val dividerItemDecoration = DividerItemDecoration(context,LinearLayoutManager.VERTICAL)
                    addItemDecoration(dividerItemDecoration)
                }

                callPutAdapter.submitData(myDate.options)
            }
        }

    }

    private val diffCallback = object : DiffUtil.ItemCallback<MyDate>(){
        override fun areItemsTheSame(oldItem: MyDate, newItem: MyDate): Boolean =
                oldItem.date == newItem.date

        override fun areContentsTheSame(
                oldItem: MyDate,
                newItem: MyDate
        ) = oldItem.hashCode() == newItem.hashCode()
    }

    private val differ = AsyncListDiffer(this,diffCallback)

    fun submitData(list: List<MyDate>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val binding = ItemOptionBinding.inflate(LayoutInflater.from(parent.context), parent,false)

        return OptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}