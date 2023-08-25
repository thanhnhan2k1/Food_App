package com.example.foodapp.data.retrofit

import com.example.foodapp.data.entities.Categories
import com.example.foodapp.data.entities.Meals
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteFoodService {
    @GET("search.php")
    suspend fun getAllMealsByFirstLetter(@Query("f") f: String): Meals

    @GET("lookup.php")
    suspend fun getMealDetailsById(@Query("i") i: String) : Meals

    @GET("search.php")
    suspend fun searchMealByName(@Query("s") s: String): Meals

    @GET("random.php")
    suspend fun getRandomMeal(): Meals

    @GET("categories.php")
    suspend fun getAllCategories(): Categories

    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") c: String): Meals
}