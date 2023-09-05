package com.example.foodappbeta.ui.meal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodappbeta.data.FoodRepository
import com.example.foodappbeta.data.model.Mapper.toMealsModel
import com.example.foodappbeta.data.model.MealModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MealViewModel(private val repository: FoodRepository): ViewModel() {

    private var _list10Meals = MutableLiveData<List<MealModel>?>()
    val list10Meals: LiveData<List<MealModel>?>
        get() = _list10Meals

    private var _mealRandom = MutableLiveData<MealModel>()
    val mealRandom: LiveData<MealModel>
        get() = _mealRandom

    private val _mealDetail = MutableLiveData<MealModel>()
    val mealDetail: LiveData<MealModel>
    get() = _mealDetail

    init {
        initListMeal()
    }

    private fun initListMeal() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllMealsByCategory()
            getRandomMeal()
        }
    }

    fun reloadListMeals() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllMeals()
        }
    }
    fun reloadMealRandom(){
        viewModelScope.launch(Dispatchers.IO) {
            getRandomMeal()
        }
    }
    private suspend fun getAllMealsByCategory(){
        repository.getAllCategories().collect{
            if(it.isEmpty()){
                repository.fetchCategories().collect{
                    repository.getRandomCategory().collect { strCategory ->
                        repository.fetchMeals(strCategory).collect { meals ->
                            _list10Meals.postValue(meals.toMealsModel().meals)
                        }
                    }
                }
            }
            else {
                repository.getRandomCategory().collect{strCategory ->
                    repository.getAllMeals(strCategory).collect{
                        _list10Meals.postValue(it)
                    }
                }
//                _listCategories.postValue(it)
            }
        }
    }

    private fun getAllMeals() {
        viewModelScope.launch(Dispatchers.IO){
            repository.getRandomCategory().collect{strCategory ->
                repository.getAllMeals(strCategory).collect{
                    if(it.isEmpty()){
                        repository.getRandomCategory().collect{strCategory ->
                            repository.fetchMeals(strCategory).collect { meals ->
                                _list10Meals.postValue(meals.toMealsModel().meals)
                            }
                        }
                    }
                    else _list10Meals.postValue(it)
                }
            }
        }

    }

    fun getCurrentMeal(): MealModel? {
        return _mealRandom.value
    }

    private suspend fun getRandomMeal() {
        repository.fetchRandomMeal().collect {
            _mealRandom.postValue(it.toMealsModel().meals.first())
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
            repository.insertMeal(meal)
        }
    }

    fun getMealByID(id: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchMealById(id).collect{meals ->
                repository.getMealFromLocalById(id).collect { meal ->
                    if(meal.idMeal.isNotEmpty()){
                        val m = meals.toMealsModel().meals.first()
                        m.isLike = meal.isLike
                        _mealDetail.postValue(m)
                    }
                    else{
                        _mealDetail.postValue(meals.toMealsModel().meals.first())
                    }
                }
            }
        }
    }

}