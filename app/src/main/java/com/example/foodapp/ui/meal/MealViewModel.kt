package com.example.foodapp.ui.meal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.FoodRepository
import com.example.foodapp.data.model.CategoryModel
import com.example.foodapp.data.model.Mapper.toCategoriesModel
import com.example.foodapp.data.model.Mapper.toMealsModel
import com.example.foodapp.data.model.MealModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MealViewModel(private val repository: FoodRepository): ViewModel() {

    private var _list10Meals = MutableLiveData<List<MealModel>?>()
    val list10Meals: LiveData<List<MealModel>?>
        get() = _list10Meals

    private var _listCategories = MutableLiveData<List<CategoryModel>?>()

    private var _meal = MutableLiveData<MealModel>()
    val meal: LiveData<MealModel>
        get() = _meal


    init {
        initListMeal()
    }

    private fun initListMeal() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllMealsByCategory()
//            getAllMeals()
            getRandomMeal()
        }
    }

    fun reloadMealsFromAPI() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllMeals()
        }
    }
    private suspend fun getAllMealsByCategory(){
        repository.getAllCategories().collect{
            if(it.isEmpty()){
                repository.fetchCategories().collect{
                    _listCategories.postValue(it.toCategoriesModel().categories)
                    repository.insertListCategories(it.toCategoriesModel().categories ?: emptyList())
                    val position = (0..(_listCategories.value?.size?.minus(1) ?: 0)).random()
                    _listCategories.value?.get(position)?.strCategory?.let {
                        repository.fetchMeals(it).collect { meals ->
                            val list = mutableListOf<MealModel>()
                            meals.toMealsModel().meals.forEach { meal ->
                                repository.fetchMealById(meal.idMeal).collect{m ->
                                    repository.insertMeal(m.toMealsModel().meals.first())
                                    list.add(m.toMealsModel().meals.first())
                                }
                            }
                            _list10Meals.postValue(list)
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
                _listCategories.postValue(it)
            }
        }
    }

    fun getAllMeals() {
        viewModelScope.launch(Dispatchers.IO){
            repository.getRandomCategory().collect{strCategory ->
                repository.getAllMeals(strCategory).collect{
                    if(it.isEmpty()){
                        val position = (0..(_listCategories.value?.size?.minus(1) ?: 0)).random()
                        _listCategories.value?.get(position)?.strCategory?.let {
                            repository.fetchMeals(it).collect { meals ->
                                val list = mutableListOf<MealModel>()
                                meals.toMealsModel().meals.forEach { meal ->
                                    repository.fetchMealById(meal.idMeal).collect{m ->
                                        repository.insertMeal(m.toMealsModel().meals.first())
                                        list.add(m.toMealsModel().meals.first())
                                    }
                                }
                                _list10Meals.postValue(list)
                            }
                        }
                    }
                    else _list10Meals.postValue(it)
                }
            }
        }

    }

    fun getCurrentMeal(): MealModel? {
        return _meal.value
    }


    private suspend fun getRandomMeal() {
        repository.fetchRandomMeal().collect {
            _meal.postValue(it.toMealsModel().meals.first())
        }
    }

    fun insertMeal(meal: MealModel) {
        viewModelScope.launch(Dispatchers.IO) {
            meal.isLike = true
            repository.insertMeal(meal)
//            this@MealViewModel.getListFavoriteMeals()
        }
    }

    fun deleteMeal(meal: MealModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMeal(meal)
//            this@MealViewModel.getListFavoriteMeals()
        }
    }

//    fun getListFavoriteMeals(): Flow<List<MealModel>> = repository.getListFavoriteMeals()




}