package com.ssjit.papertrading.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssjit.papertrading.data.models.ProfileItem
import com.ssjit.papertrading.databinding.ProfileItemBinding

class ProfileItemAdapter(
        private val onClick: (String?) -> Unit
): RecyclerView.Adapter<ProfileItemAdapter.ProfileItemViewHolder>() {

    inner class ProfileItemViewHolder(private val binding:ProfileItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(currentItem: ProfileItem?) {
            currentItem?.let { item->
                binding.apply {
                    tvCaption.text = item.caption
                    if (!item.text.isNullOrEmpty()){
                        tvText.isVisible = true
                        imgItem.isVisible = false
                        tvText.text = item.text
                    }else if (item.image != null){
                        tvText.isVisible = false
                        imgItem.isVisible = true
                        Glide.with(this.root)
                                .load(item.image)
                                .into(imgItem)
                    }
                }
            }
        }

    }

    private val diffCallback = object : DiffUtil.ItemCallback<ProfileItem>(){
        override fun areItemsTheSame(oldItem: ProfileItem, newItem: ProfileItem): Boolean =
                oldItem.itemType == newItem.itemType

        override fun areContentsTheSame(
                oldItem: ProfileItem,
                newItem: ProfileItem
        ) = oldItem == newItem
    }

    private val differ = AsyncListDiffer(this,diffCallback)

    fun submitData(list: List<ProfileItem>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileItemViewHolder {
        val binding = ProfileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ProfileItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProfileItemViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.bind(currentItem)
        holder.itemView.setOnClickListener {
            onClick(currentItem.itemType)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}