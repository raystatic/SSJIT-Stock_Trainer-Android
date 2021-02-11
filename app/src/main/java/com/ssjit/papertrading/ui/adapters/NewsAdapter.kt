package com.ssjit.papertrading.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ssjit.papertrading.data.models.FNO.Future
import com.ssjit.papertrading.data.models.FNO.MyDate
import com.ssjit.papertrading.data.models.news.News
import com.ssjit.papertrading.databinding.NewsItemBinding

class NewsAdapter(
        private val onClicked:(String) -> Unit
):RecyclerView.Adapter<NewsAdapter.NewsViewHodler>() {

    inner class NewsViewHodler(val binding:NewsItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item: News) {
            binding.apply {
                tvTiltle.text = item.title
                root.setOnClickListener {
                    onClicked(item.link)
                }
            }
        }

    }


    private val diffCallback = object : DiffUtil.ItemCallback<News>(){
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean =
                oldItem.uuid == newItem.uuid

        override fun areContentsTheSame(
                oldItem: News,
                newItem: News
        ) = oldItem.hashCode() == newItem.hashCode()
    }

    private val differ = AsyncListDiffer(this,diffCallback)

    fun submitData(list: List<News>) = differ.submitList(list)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.NewsViewHodler {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return NewsViewHodler(binding)
    }

    override fun onBindViewHolder(holder: NewsAdapter.NewsViewHodler, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}