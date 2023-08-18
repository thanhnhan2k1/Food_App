package com.example.foodapp.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private vararg val params: Any) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        try {
            val constructor = modelClass.getDeclaredConstructor(*params.map { it::class.java }.toTypedArray())
            return constructor.newInstance(*params) as T
        } catch (e: Exception) {
            throw RuntimeException("Failed to create ViewModel instance", e)
        }
    }

}