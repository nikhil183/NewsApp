package com.example.newsapp.model.network

sealed class NetworkResult<T>(val data: T? = null, val message: String? = null) {
    class Loading<T> : NetworkResult<T>()
    class Failure<T>(message: String?) : NetworkResult<T>(message = message)
    class Success<T>(data: T) : NetworkResult<T>(data)
}