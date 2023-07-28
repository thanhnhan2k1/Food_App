package com.example.foodapp.ui.meal

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodapp.data.room.MealDAO
import com.example.foodapp.data.room.MealRepository

class MealViewModelFactory(
    private val dataSource: MealDAO,
    private val repository: MealRepository,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealViewModel::class.java)) {
            return MealViewModel(dataSource, repository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}