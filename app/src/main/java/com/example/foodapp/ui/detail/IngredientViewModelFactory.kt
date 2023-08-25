package com.example.foodapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodapp.data.model.MealModel

class IngredientViewModelFactory(): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(IngredientViewModel::class.java)){
            return IngredientViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}