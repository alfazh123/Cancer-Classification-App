package com.dicoding.asclepius.view.adapter

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.remote.response.ArticlesItem
import com.dicoding.asclepius.databinding.NewsListBinding

class NewsAdapter: ListAdapter<ArticlesItem, NewsAdapter.NewsViewHolder>(DIFF_CALLBACK) {


    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = NewsListBinding.bind(itemView)

        fun bind(news: ArticlesItem) {
            binding.tvTitle.text = news.title
            Glide.with(itemView.context)
                .load(news.urlToImage)
                .into(binding.ivImage)
            binding.tvDescription.text = news.description
            Log.d("NewsAdapter It Work", "bind: ${news.title}")

            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(news.url)
                itemView.context.startActivity(intent)
            }
        }

    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticlesItem>() {
            override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = LayoutInflater.from(parent.context).inflate(R.layout.news_list, parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}