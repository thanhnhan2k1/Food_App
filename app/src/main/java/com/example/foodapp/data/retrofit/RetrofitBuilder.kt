package com.example.foodapp.data.retrofit


import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"
object RetrofitBuilder {

    fun getApiInterface(): ApiInterface {

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
//            .client(OkHttpClient())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(ApiInterface::class.java)
    }
}