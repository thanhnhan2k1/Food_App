package com.example.foodappbeta.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodappbeta.data.FoodRepository
import com.example.foodappbeta.ui.meal.MealViewModel
import com.example.foodappbeta.ui.saved.SavedViewModel
import com.example.foodappbeta.ui.search.SearchViewModel

class MealViewModelFactory(
    private val repository: FoodRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealViewModel::class.java)) {
            return MealViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(SavedViewModel::class.java)) {
            return SavedViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}