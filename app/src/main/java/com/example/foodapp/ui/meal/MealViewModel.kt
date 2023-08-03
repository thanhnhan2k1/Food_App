package com.example.foodapp.ui.meal

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.retrofit.MealApi
import com.example.foodapp.data.room.MealDAO
import com.example.foodapp.model.CategoryModel
import com.example.foodapp.model.MealModel
import com.example.foodapp.model.toCatogoriesModel
import com.example.foodapp.model.toMealsModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class MealViewModel(
    private var database: MealDAO,
    application: Application
) :
    AndroidViewModel(application) {

    private var _list10Meals = MutableLiveData<List<MealModel>?>()
    val list10Meals: LiveData<List<MealModel>?>
        get() = _list10Meals

    private var _listCategories = MutableLiveData<List<CategoryModel>?>()

    private var _meal = MutableLiveData<MealModel>()
    val meal: LiveData<MealModel>
        get() = _meal

    private var _listFilterMeals = MutableLiveData<List<MealModel>?>()
    val listFilterMeals: LiveData<List<MealModel>?>
        get() = _listFilterMeals

    init {
        initListMeal()
    }

    private fun initListMeal() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllMeals()
            getRandomMeal()
            getListFavoriteMeals()
        }
    }

    private suspend fun getAllMeals() {
        MealApi().fetchCategories().collect {
            withContext(Dispatchers.Main) {
                _listCategories.value = it.toCatogoriesModel().categories
            }

        }

        if (_listCategories.value?.isEmpty() == true) {
            MealApi().fetchMeals("Seafood").collect {
                withContext(Dispatchers.Main) {
                    _list10Meals.value = it.toMealsModel().meals
                }
            }
        } else {
            val position = (0..(_listCategories.value?.size?.minus(1) ?: 0)).random()
            _listCategories.value?.get(position)?.strCategory?.let {
                MealApi().fetchMeals(it).collect {
                    withContext(Dispatchers.Main) {
                        val listMeals = it.toMealsModel().meals
                        val list = mutableListOf<MealModel>()
                        for(item in listMeals) MealApi().fetchMealById(item.idMeal).collect {
                            list.add(it.toMealsModel().meals[0])
                        }
                        _list10Meals.value = list
                    }
                }
            }
        }
    }

    fun getCurrentMeal(): MealModel? {
        return _meal.value
    }


    private suspend fun getRandomMeal() {
        MealApi().fetchRandomMeal().collect {
            withContext(Dispatchers.Main) {
                _meal.value = it.toMealsModel().meals[0]
            }
        }
    }

    fun insertMeal(meal: MealModel) {
        viewModelScope.launch(Dispatchers.IO) {
            meal.isLike = true
            database.insertMeal(meal)
            this@MealViewModel.getListFavoriteMeals()
        }
    }

    fun deleteMeal(meal: MealModel) {
        viewModelScope.launch(Dispatchers.IO) {
            database.deleteMeal(meal)
            this@MealViewModel.getListFavoriteMeals()
        }
    }

    fun getListFavoriteMeals(): Flow<List<MealModel>> = database.getAllMeals()
        .catch {
            Log.d("Database", "Get data fail!")
            emit(emptyList())
        }

    fun getListMealsByFirstLetter(key: String) {
        viewModelScope.launch(Dispatchers.IO) {
            MealApi().fetchMealsByFirstLetter(key).collect {
                withContext(Dispatchers.Main) {
                    _listFilterMeals.value = it.toMealsModel().meals
                }
            }
        }
    }

}