package com.example.foodapp.model

import android.content.Context
import com.example.foodapp.data.room.FoodDatabase
import com.example.foodapp.data.room.MealDAO

object Constants {
    const val ROOT_CLICK = 0
    const val LIKE_CLICK = 1
    const val UNLIKE_CLICK = 2
    fun getDatasource(context: Context): MealDAO {
        return FoodDatabase.getDatabase(context).mealDAO()
    }
}