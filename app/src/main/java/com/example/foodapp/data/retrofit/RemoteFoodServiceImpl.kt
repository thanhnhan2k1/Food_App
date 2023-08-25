package com.example.foodapp.data.retrofit


import com.example.foodapp.data.model.Constant
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RemoteFoodServiceImpl {
    fun getRemoteFoodService(): RemoteFoodService {

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constant.BASE_URL)
//            .client(OkHttpClient())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(RemoteFoodService::class.java)
    }
}