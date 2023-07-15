package com.example.newsapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.model.network.MyFirebaseMessagingService.Companion.TOPIC
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : AppCompatActivity() {

    private lateinit var dataBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
    }

}