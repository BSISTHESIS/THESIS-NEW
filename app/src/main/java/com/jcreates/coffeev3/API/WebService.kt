package com.joey.kotlinandroidbeginning.API


import retrofit2.Retrofit
import okhttp3.OkHttpClient
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.converter.gson.GsonConverterFactory
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import java.util.concurrent.TimeUnit

object WebService {

//     const val uRL = "http://192.168.6.114:8080/coffee_project/"
//     const val uRL2= "http://192.168.6.114:8080/coffee_project/API_COFFEE"

    const val uRL = "http://coms.adwork1.online/"
    const val uRL2= "http://coms.adwork1.online/API_COFFEE"


// retrofit



    const val appKey = "@dm1nC0fF33"
    private var retrofit: Retrofit? = null
    val apiLink: Retrofit?
        get() {
            val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .build()
            val gson = GsonBuilder()
                .setLenient()
                .create()
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(uRL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build()
            }
            return retrofit
        }
}