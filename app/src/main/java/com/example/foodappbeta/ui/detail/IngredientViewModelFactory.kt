package com.example.foodappbeta.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class IngredientViewModelFactory(): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(IngredientViewModel::class.java)){
            return IngredientViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}