package com.example.foodapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.entities.Ingredient
import com.example.foodapp.data.model.Mapper.toListIngredient
import com.example.foodapp.data.model.MealModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IngredientViewModel : ViewModel() {
    private val _listIngredient = MutableLiveData<List<Ingredient>>()
    val listIngredient: LiveData<List<Ingredient>>
        get() = _listIngredient

    private val _meal = MutableLiveData<MealModel>()
    val meal : LiveData<MealModel>
    get() = _meal

    fun setListIngredient() {
        viewModelScope.launch(Dispatchers.IO){
            _listIngredient.postValue(_meal.value?.toListIngredient())
        }
    }

    fun setMeal(meal: MealModel){
        viewModelScope.launch(Dispatchers.IO) {
            _meal.postValue(meal)
        }
    }

}