/* Copyright (C) Wasteless - All Rights Reserved

 * Unauthorized copying of this file, via any medium is strictly prohibited

 * Proprietary and confidential

 * Written by Siddharth Vijayasankar <sidvijay2004@gmail.com>, January 2021

 */

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