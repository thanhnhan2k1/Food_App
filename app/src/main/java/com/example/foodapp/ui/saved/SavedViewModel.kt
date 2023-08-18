package com.example.foodapp.ui.saved

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.FoodRepository
import com.example.foodapp.data.model.MealModel
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
}