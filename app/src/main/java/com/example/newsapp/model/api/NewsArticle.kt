package com.example.newsapp.model.api

data class NewsArticle(
    val title: String,
    val url: String,
    val publishedAt: String,
    val author: String,
    val urlToImage: String
)