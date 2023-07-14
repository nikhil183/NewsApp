package com.example.newsapp.model.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.ArticleListItemBinding
import com.example.newsapp.model.api.NewsArticle

class ArticlesAdapter(
    private var articles: MutableList<NewsArticle>
) : RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {

    private lateinit var dataBinding: ArticleListItemBinding
    private lateinit var clickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        clickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        dataBinding =
            ArticleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(dataBinding, clickListener)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.dataBinding.article = article
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    class ArticleViewHolder(
        val dataBinding: ArticleListItemBinding,
        private val clickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(dataBinding.root) {
        init {
            dataBinding.root.setOnClickListener {
                clickListener.onClick(adapterPosition)
            }
        }
    }
}