package com.example.newsapp.model.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.NewsArticleListItemBinding
import com.example.newsapp.model.api.NewsArticle

class NewsArticleAdapter(
    private var newsArticles: MutableList<NewsArticle>
) : RecyclerView.Adapter<NewsArticleAdapter.NewsArticleViewHolder>() {

    private lateinit var dataBinding: NewsArticleListItemBinding
    private lateinit var clickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        clickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsArticleViewHolder {
        dataBinding =
            NewsArticleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsArticleViewHolder(dataBinding, clickListener)
    }

    override fun onBindViewHolder(holder: NewsArticleViewHolder, position: Int) {
        val newsArticle = newsArticles[position]
        holder.dataBinding.newsArticle = newsArticle
    }

    override fun getItemCount(): Int {
        return newsArticles.size
    }

    class NewsArticleViewHolder(
        val dataBinding: NewsArticleListItemBinding,
        private val clickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(dataBinding.root) {
        init {
            dataBinding.root.setOnClickListener {
                clickListener.onClick(adapterPosition)
            }
        }
    }
}