package com.example.newsapp.model.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.URL
import javax.net.ssl.HttpsURLConnection

object BindingAdapters {
    @BindingAdapter("android:src")
    @JvmStatic
    fun setImageViewResource(imageView: ImageView, imageUrl: String?) {
        CoroutineScope(Dispatchers.Main).launch {
            val bitmap = withContext(Dispatchers.IO) {
                downloadImage(imageUrl!!)
            }
            imageView.setImageBitmap(bitmap)
        }
    }

    private suspend fun downloadImage(url: String): Bitmap? {
        var bitmap: Bitmap? = null
        withContext(Dispatchers.IO) {
            try {
                val imageUrl = URL(url)
                val connection = imageUrl.openConnection() as HttpsURLConnection
                connection.doInput = true
                connection.connect()
                val inputStream: InputStream = connection.inputStream
                bitmap = BitmapFactory.decodeStream(inputStream)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
        return bitmap
    }
}