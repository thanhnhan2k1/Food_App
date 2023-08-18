package com.example.foodapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodapp.data.model.MealModel

class IngredientViewModelFactory(private val meal: MealModel): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(IngredientViewModel::class.java)){
            return IngredientViewModel(meal) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}