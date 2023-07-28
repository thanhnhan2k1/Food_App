package com.example.foodapp.data.retrofit

import com.example.foodapp.model.Categories
import com.example.foodapp.model.Meals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MealApi {
    fun fetchMeals(category: String) : Flow<Meals> = flow {
        emit(RetrofitBuilder.getApiInterface().getMealsByCategory(category))
    }
    fun fetchCategories(): Flow<Categories> = flow {
        emit(RetrofitBuilder.getApiInterface().getAllCategories())
    }
    fun fetchRandomMeal() : Flow<Meals> = flow {
        emit(RetrofitBuilder.getApiInterface().getRandomMeal())
    }
    fun fetchMealByName(name: String): Flow<Meals> = flow {
        emit(RetrofitBuilder.getApiInterface().searchMealByName(name))
    }
    fun fetchMealsByFirstLetter(key: String): Flow<Meals> = flow {
        emit(RetrofitBuilder.getApiInterface().getAllMealsByFirstLetter(key))
    }
}