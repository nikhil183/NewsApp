package com.example.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.repository.NewsArticleRepository

class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        NewsArticleViewModel::class.java -> NewsArticleViewModel(NewsArticleRepository())
        else -> throw IllegalArgumentException("No ViewModel registered for $modelClass")
    } as T

}