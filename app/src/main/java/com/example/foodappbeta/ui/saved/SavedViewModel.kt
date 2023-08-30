package com.example.foodappbeta.ui.saved

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodappbeta.data.FoodRepository
import com.example.foodappbeta.data.model.MealModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SavedViewModel(private val repository: FoodRepository): ViewModel() {
    private var _listFavoriteMeals = MutableLiveData<List<MealModel>?>()
    val listFavoriteMeals: LiveData<List<MealModel>?>
        get() = _listFavoriteMeals
    init {
        getListFavoriteMeals()
    }
    fun getListFavoriteMeals(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getListFavoriteMeals().collect{
                _listFavoriteMeals.postValue(it)
            }
        }
    }

    fun insertFavoriteMeal(meal: MealModel) {
        viewModelScope.launch(Dispatchers.IO) {
            meal.isLike = true
            repository.insertMeal(meal)
        }
    }

    fun deleteFavoriteMeal(meal: MealModel) {
        viewModelScope.launch(Dispatchers.IO) {
            meal.isLike = false
            repository.deleteMeal(meal)
            getListFavoriteMeals()
        }
    }
}