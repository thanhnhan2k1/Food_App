package com.example.foodapp.data.retrofit

import android.util.Log
import com.example.foodapp.model.Categories
import com.example.foodapp.model.Meals
import kotlinx.coroutines.flow.*

class MealApi {
    fun fetchMeals(category: String): Flow<Meals> = flow {
        emit(RetrofitBuilder.getApiInterface().getMealsByCategory(category))
    }.catch {
        val meals = Meals(emptyList())
        Log.d("API", "Get data fail!")
        this.emit(meals)
    }

    fun fetchCategories(): Flow<Categories> = flow {
        emit(RetrofitBuilder.getApiInterface().getAllCategories())
    }.catch {
        Log.d("API", "Get data fail!")
        val categories = Categories(emptyList())
        this.emit(categories)
    }

    fun fetchRandomMeal(): Flow<Meals> = flow {
        emit(RetrofitBuilder.getApiInterface().getRandomMeal())
    }.catch { e ->
        Log.d("API", "Get data fail!")
    }

    fun fetchMealByName(name: String): Flow<Meals> = flow {
        emit(RetrofitBuilder.getApiInterface().searchMealByName(name))
    }.catch { e ->
        Log.d("API", "Get data fail!")
    }

    fun fetchMealsByFirstLetter(key: String): Flow<Meals> = flow {
        emit(RetrofitBuilder.getApiInterface().getAllMealsByFirstLetter(key))
    }.catch { e ->
        Log.d("API", "Get data fail!")
    }
}