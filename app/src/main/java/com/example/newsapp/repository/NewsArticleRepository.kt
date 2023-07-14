package com.example.newsapp.repository

import android.util.Log
import com.example.newsapp.contants.UrlConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class NewsArticleRepository {
    suspend fun getNewsArticles(): String {
        val jsonStringHolder = StringBuilder()
        withContext(Dispatchers.IO) {
            var httpsURLConnection: HttpsURLConnection? = null
            try {
                val url = URL(UrlConstants.API_URL)
                httpsURLConnection = url.openConnection() as HttpsURLConnection

                val code = httpsURLConnection.responseCode
                if (code != 200) {
                    throw IOException("Something went wrong!!")
                }

                val bufferedReader =
                    BufferedReader(InputStreamReader(httpsURLConnection.inputStream))

                while (true) {
                    val readLine = bufferedReader.readLine() ?: break
                    jsonStringHolder.append(readLine)
                }
            } catch (ioException: IOException) {
                Log.e(this.javaClass.name, ioException.message.toString())
                throw IOException("Something went wrong!!")
            } finally {
                httpsURLConnection?.disconnect()
            }
        }

        return jsonStringHolder.toString()
    }
}