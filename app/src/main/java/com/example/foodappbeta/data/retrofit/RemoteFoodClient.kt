package com.example.foodappbeta.data.retrofit


import com.example.foodappbeta.data.model.Constant
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RemoteFoodClient {
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