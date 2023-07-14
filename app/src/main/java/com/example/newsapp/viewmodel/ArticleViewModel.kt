package com.example.newsapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.api.NewsApiResponse
import com.example.newsapp.model.api.NewsArticle
import com.example.newsapp.model.network.NetworkResult
import com.example.newsapp.repository.ArticlesRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class ArticleViewModel(private val repository: ArticlesRepository) : ViewModel() {
    private val _articles = MutableLiveData<NetworkResult<List<NewsArticle>>>()
    val articles: LiveData<NetworkResult<List<NewsArticle>>> = _articles

    fun getNewsArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            _articles.postValue(NetworkResult.Loading())
            try {
                val repositoryResponse = repository.getNewsArticles()
                val articles =
                    Gson().fromJson(repositoryResponse, NewsApiResponse::class.java)
                if (articles.status == "ok") {
                    _articles.postValue(NetworkResult.Success(articles.articles))
                } else {
                    _articles.postValue(NetworkResult.Failure("Something went wrong!!"))
                }
            } catch (ioException: IOException) {
                _articles.postValue(NetworkResult.Failure("Something went wrong!!"))
                Log.e(this.javaClass.name, ioException.message.toString())
            }
        }
    }
}