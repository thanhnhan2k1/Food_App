package com.example.foodapp.ui.meal

import android.util.Log
import androidx.lifecycle.*
import com.example.foodapp.data.retrofit.MealRepository
import com.example.foodapp.data.room.MealDAO
import com.example.foodapp.model.*
import com.example.foodapp.model.Mapper.toCategoriesModel
import com.example.foodapp.model.Mapper.toMealsModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class MealViewModel(private val database: MealDAO): ViewModel() {

    private var _list10Meals = MutableLiveData<List<MealModel>?>()
    val list10Meals: LiveData<List<MealModel>?>
        get() = _list10Meals

    private var _listCategories = MutableLiveData<List<CategoryModel>?>()
    val listCategories: LiveData<List<CategoryModel>?>
        get() = _listCategories

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
            getAllCategories()
            getRandomMeal()
            getListFavoriteMeals()
        }
    }

    fun reloadMealsFromAPI() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllMeals()
        }
    }
    private suspend fun getAllCategories(){
        MealRepository.fetchCategories().collect {
            _listCategories.postValue(it.toCategoriesModel().categories)
        }
    }

    fun getAllMeals() {
        viewModelScope.launch(Dispatchers.IO){
            if (_listCategories.value?.isEmpty() == true) {
                MealRepository.fetchMeals("Seafood").collect { it ->
                    val listMeals = it.toMealsModel().meals
                    val list = mutableListOf<MealModel>()
                    for (item in listMeals) MealRepository.fetchMealById(item.idMeal).collect {
                        list.add(it.toMealsModel().meals[0])
                    }
                    _list10Meals.postValue(list)
                }
            } else {
                val position = (0..(_listCategories.value?.size?.minus(1) ?: 0)).random()
                _listCategories.value?.get(position)?.strCategory?.let { it ->
                    MealRepository.fetchMeals(it).collect { m ->
                        val listMeals = m.toMealsModel().meals
                        val list = mutableListOf<MealModel>()
                        for (item in listMeals) MealRepository.fetchMealById(item.idMeal).collect {
                            list.add(it.toMealsModel().meals[0])
                        }
                        _list10Meals.postValue(list)
                    }
                }
            }
        }

    }

    fun getCurrentMeal(): MealModel? {
        return _meal.value
    }


    private suspend fun getRandomMeal() {
        MealRepository.fetchRandomMeal().collect {
            _meal.postValue(it.toMealsModel().meals[0])
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
            MealRepository.fetchMealsByFirstLetter(key).collect {
                _listFilterMeals.postValue(it.toMealsModel().meals)
            }
        }
    }

    fun getMealByName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            MealRepository.fetchMealByName(name).collect {
                _listFilterMeals.postValue(it.toMealsModel().meals)
            }
        }
    }

}