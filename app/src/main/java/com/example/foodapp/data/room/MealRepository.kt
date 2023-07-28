package com.example.foodapp.data.room

import com.example.foodapp.model.Meal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MealRepository(private val mealDAO: MealDAO) {
    fun getAllMeals(): Flow<List<Meal>> = flow{
        emit(mealDAO.getAllMeals())
    }
}