package com.example.foodappbeta.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodappbeta.data.FoodRepository
import com.example.foodappbeta.data.model.Mapper.toMealsModel
import com.example.foodappbeta.data.model.MealModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: FoodRepository): ViewModel() {
    private var _listFilterMeals = MutableLiveData<List<MealModel>?>()
    val listFilterMeals: LiveData<List<MealModel>?>
        get() = _listFilterMeals

    fun getListMealsByFirstLetter(key: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchMealsByFirstLetter(key).collect {
                _listFilterMeals.postValue(it.toMealsModel().meals)
            }
        }
    }

    fun getMealByName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchMealByName(name).collect {
                _listFilterMeals.postValue(it.toMealsModel().meals)
            }
        }
    }
}