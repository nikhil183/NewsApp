package com.example.newsapp.model.network

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.newsapp.R
import com.example.newsapp.view.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        message.data.isNotEmpty().let {
            if (it) {
                // Perform necessary operations. I am just showing notification.
                generateNotification(message.data[DATA_TITLE] ?: "", message.data[DATA_MESSAGE] ?: "")
            }
        }

        message.notification?.let {
            generateNotification(it.title ?: "", it.body ?: "")
        }
    }

    private fun generateNotification(title: String, message: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle(title).setContentText(message).setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true).setContentIntent(pendingIntent)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0, builder.build())
    }

    companion object {
        const val CHANNEL_ID = "notification_channel"
        const val CHANNEL_NAME = "com.example.newsapp"

        const val DATA_TITLE = "title"
        const val DATA_MESSAGE = "message"

        const val TOPIC = "/topics/my_topic"
    }
}