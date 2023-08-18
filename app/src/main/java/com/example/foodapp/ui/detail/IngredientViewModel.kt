package com.example.foodapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.entity.Ingredient
import com.example.foodapp.data.model.Mapper.toListIngredient
import com.example.foodapp.data.model.MealModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IngredientViewModel(private val meal: MealModel) : ViewModel() {
    private val _listIngredient = MutableLiveData<List<Ingredient>>()
    val listIngredient: LiveData<List<Ingredient>>
        get() = _listIngredient

    fun setListIngredient() {
        viewModelScope.launch(Dispatchers.IO){
            _listIngredient.postValue(meal.toListIngredient())
        }
    }

}