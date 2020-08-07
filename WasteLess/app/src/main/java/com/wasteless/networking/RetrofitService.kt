package com.wasteless.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {
    //    private val url = "http://13.59.14.86:8080/" //CLOUD
    //private val url = "http://localhost:8081/" //CLOUD
    private val url = "http://192.168.1.110:8081/" //CLOUD
    // Change url for cloud
    //private val url = "http://10.0.2.2:8081/" //LOCAL
    //private val url = "https://hkms8t0n40.execute-api.us-east-1.amazonaws.com/Prod/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    public fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }
}