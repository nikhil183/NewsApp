package com.example.newsapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.api.NewsApiResponse
import com.example.newsapp.model.api.NewsArticle
import com.example.newsapp.model.network.NetworkResult
import com.example.newsapp.repository.NewsArticleRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class NewsArticleViewModel(private val repository: NewsArticleRepository) : ViewModel() {
    private val _newsArticles = MutableLiveData<NetworkResult<List<NewsArticle>>>()
    val newsArticles: LiveData<NetworkResult<List<NewsArticle>>> = _newsArticles

    fun getNewsArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            _newsArticles.postValue(NetworkResult.Loading())
            try {
                val repositoryResponse = repository.getNewsArticles()
                val newsArticles =
                    Gson().fromJson(repositoryResponse, NewsApiResponse::class.java)
                if (newsArticles.status == "ok") {
                    _newsArticles.postValue(NetworkResult.Success(newsArticles.articles))
                } else {
                    _newsArticles.postValue(NetworkResult.Failure("Something went wrong!!"))
                }
            } catch (ioException: IOException) {
                _newsArticles.postValue(NetworkResult.Failure("Something went wrong!!"))
                Log.e(this.javaClass.name, ioException.message.toString())
            }
        }
    }
}