package com.wasteless.activities

import android.os.Bundle
import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity

open class CustomAppActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.let {actionBar ->
            actionBar.hide()
        }

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build() //For accessing localhost or any http request
        StrictMode.setThreadPolicy(policy)

    }

}