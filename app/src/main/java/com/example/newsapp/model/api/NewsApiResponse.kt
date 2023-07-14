package com.example.newsapp.model.api

data class NewsApiResponse(
    val status: String,
    val articles: List<NewsArticle>
)