package com.example.foodapp.data

import android.util.Log
import com.example.foodapp.data.entities.Categories
import com.example.foodapp.data.entities.Meals
import com.example.foodapp.data.model.CategoryModel
import com.example.foodapp.data.model.Constant
import com.example.foodapp.data.model.MealModel
import com.example.foodapp.data.retrofit.RemoteFoodService
import com.example.foodapp.data.retrofit.RemoteFoodServiceImpl
import com.example.foodapp.data.room.CategoryDAO
import com.example.foodapp.data.room.LocalFoodService
import com.example.foodapp.data.room.MealDAO
import kotlinx.coroutines.flow.*

class FoodRepository(val remote : RemoteFoodService, val mealDAO: MealDAO, val categoryDAO: CategoryDAO) {
    fun fetchMeals(category: String): Flow<Meals> = flow {
        emit(remote.getMealsByCategory(category))
    }.catch {
        val meals = Meals(emptyList())
        Log.d("API", "Get list meals fail!")
        this.emit(meals)
    }

    fun fetchCategories(): Flow<Categories> = flow {
        emit(remote.getAllCategories())
    }.catch {
        Log.d("API", "Get list categories fail!")
        val categories = Categories(emptyList())
        this.emit(categories)
    }

    fun fetchRandomMeal(): Flow<Meals> = flow {
        emit(remote.getRandomMeal())
    }.catch {
        Log.d("API", "Get random meal fail!")
    }

    fun fetchMealByName(name: String): Flow<Meals> = flow {
        emit(RemoteFoodServiceImpl.getRemoteFoodService().searchMealByName(name))
    }.catch {
        Log.d("API", "Get data fail!")
    }

    fun fetchMealsByFirstLetter(key: String): Flow<Meals> = flow {
        emit(remote.getAllMealsByFirstLetter(key))
    }.catch {
        Log.d("API", "Get meals by first letter fail!")
    }

    fun fetchMealById(id: String): Flow<Meals> = flow {
        emit(remote.getMealDetailsById(id))
    }.catch {
        Log.d("API", "Get meal by id fail!")
    }

    fun insertMeal(mealModel: MealModel) {
        mealDAO.insertMeal(mealModel)
    }

    fun deleteMeal(mealModel: MealModel) {
        mealDAO.deleteMeal(mealModel)
    }

    fun getAllMeals(category: String): Flow<List<MealModel>> = flow {
        emit(mealDAO.getAllMealsByCategory(category))
    }.catch {
        emit(emptyList())
    }

    fun getListFavoriteMeals(): Flow<List<MealModel>> = flow {
        emit(mealDAO.getListFavoriteMeals())
    }.catch {
        emit(emptyList())
    }

    fun getAllCategories(): Flow<List<CategoryModel>> = flow {
        emit(categoryDAO.getAllCategories())
    }.catch {
        emit(emptyList())
    }

    fun getRandomCategory(): Flow<String> = flow {
        emit(categoryDAO.getRandomCategory() ?: "")
    }.catch {
        emit(Constant.DEFAULT_CATEGORY)
    }

    fun insertListCategories(list: List<CategoryModel>) {
        categoryDAO.insertListCategories(list)
    }

}