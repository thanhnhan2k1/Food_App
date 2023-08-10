package com.example.foodapp.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if(modelClass.isAssignableFrom(IngredientViewModel::class.java)){
//            return IngredientViewModel(meal = MealModel()) as T
//        }
//        if(modelClass.isAssignableFrom(MealViewModel::class.java)){
//            return MealViewModel(database = ) as T
//        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}