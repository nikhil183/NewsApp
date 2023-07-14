package com.example.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.repository.ArticlesRepository

class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        ArticleViewModel::class.java -> ArticleViewModel(ArticlesRepository())
        else -> throw IllegalArgumentException("No ViewModel registered for $modelClass")
    } as T

}