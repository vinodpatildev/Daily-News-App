package com.vinodpatildev.dailynews.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vinodpatildev.dailynews.data.model.Article
import com.vinodpatildev.dailynews.databinding.ListItemBinding

class NewsAdapter:RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private val callback = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val itemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NewsViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class NewsViewHolder(private val binding:ListItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(article:Article){
            binding.tvTitle.text  = article.title
            binding.tvDescription.text = article.description
            binding.tvSource.text = article.source?.name
            binding.tvPublishedAt.text = article.publishedAt

            Glide.with(binding.ivArticleImage.context)
                .load(article.urlToImage)
                .into(binding.ivArticleImage)

            binding.root.setOnClickListener{
                onItemClickListener?.let {
                    it(article)
                }
            }
        }
    }

    private var onItemClickListener : ((Article)->Unit)? = null

    fun setOnItemClickListener(listener:((Article)->Unit)){
        onItemClickListener = listener
    }
}