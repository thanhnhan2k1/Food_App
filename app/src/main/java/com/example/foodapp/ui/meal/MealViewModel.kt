package com.example.foodapp.ui.meal

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.retrofit.MealApi
import com.example.foodapp.model.Category
import com.example.foodapp.model.Meal
import com.example.foodapp.data.room.MealDAO
import com.example.foodapp.data.room.MealRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch

class MealViewModel(
    private var database: MealDAO,
    private val repository: MealRepository,
    application: Application
) :
    AndroidViewModel(application) {

    private var _list10Meals = MutableLiveData<List<Meal>?>()
    val list10Meals: LiveData<List<Meal>?>
        get() = _list10Meals
//    private var _listFavoriteMeals = MutableLiveData<List<Meal>?>()
//    val listFavoriteMeals: LiveData<List<Meal>?>
//        get() = _listFavoriteMeals

    private var _listCategories = MutableLiveData<List<Category>?>()


    //    val listCategories: LiveData<List<Category>?>
//        get() = _listCategories
    private var _meal = MutableLiveData<Meal>()
    val meal: LiveData<Meal>
        get() = _meal
    private var _mealItem = MutableLiveData<Meal>()

    //    val mealItem: LiveData<Meal>
//        get() = _mealItem
    private var _listFilterMeals = MutableLiveData<List<Meal>?>()
    val listFilterMeals: LiveData<List<Meal>?>
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
            _listCategories.postValue(it.categories)

        }
        val position = (0..(_listCategories.value?.size?.minus(1) ?: 0)).random()

        if(_listCategories.value?.isEmpty() == true){
            withContext(Dispatchers.Main) {
                _list10Meals.value = emptyList()
            }
        }
        else{
            _listCategories.value?.get(position)?.strCategory?.let {
                MealApi().fetchMeals(it).collect {
//                _list10Meals.postValue(it.meals)
                    withContext(Dispatchers.Main) {
                        _list10Meals.value = it.meals
//                    Log.i("MealViewModel", list10Meals.value.toString())
                    }
                }
            }
        }
    }

    fun getCurrentMeal(): Meal? {
        return _meal.value
    }

    fun getMealItem(name: String): Meal? {
        getMealByName(name)
        return _mealItem.value
    }

    private suspend fun getRandomMeal() {
        MealApi().fetchRandomMeal().collect {
            withContext(Dispatchers.Main) {
                _meal.value = it.meals?.get(0)
//                Log.i("MealViewModel", _meal.value?.strMealThumb.toString())
            }
        }
    }

    private fun getMealByName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            MealApi().fetchMealByName(name).collect {
                withContext(Dispatchers.Main) {
                    _mealItem.value = it.meals?.get(0)
                }
            }
        }
    }

    fun insertMeal(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) {
            database.insertMeal(meal)
            this@MealViewModel.getListFavoriteMeals()
        }
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch(Dispatchers.IO) {
            database.deleteMeal(meal)
            this@MealViewModel.getListFavoriteMeals()
        }
    }

    fun getListFavoriteMeals() : Flow<List<Meal>> = database.getAllMeals()
        .catch {
            Log.d("Database", "Get data fail!")
            emit(emptyList())
        }

    fun getListMealsByFirstLetter(key: String) {
        viewModelScope.launch(Dispatchers.IO) {
            MealApi().fetchMealsByFirstLetter(key).collect {
                withContext(Dispatchers.Main) {
                    _listFilterMeals.value = it.meals
                }
            }
        }
    }

}