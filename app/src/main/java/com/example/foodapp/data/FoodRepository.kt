package com.example.foodapp.data

import android.content.Context
import android.util.Log
import com.example.foodapp.data.entity.Categories
import com.example.foodapp.data.entity.Meals
import com.example.foodapp.data.model.CategoryModel
import com.example.foodapp.data.model.Constant
import com.example.foodapp.data.model.MealModel
import com.example.foodapp.data.retrofit.RetrofitBuilder
import com.example.foodapp.data.room.FoodDatabase
import kotlinx.coroutines.flow.*

class FoodRepository(context: Context) {
    private val api = RetrofitBuilder.getApiInterface()
    private val database = FoodDatabase.getDatabase(context)
    fun fetchMeals(category: String): Flow<Meals> = flow {
        emit(api.getMealsByCategory(category))
    }.catch {
        val meals = Meals(emptyList())
        Log.d("API", "Get list meals fail!")
        this.emit(meals)
    }

    fun fetchCategories(): Flow<Categories> = flow {
        emit(api.getAllCategories())
    }.catch {
        Log.d("API", "Get list categories fail!")
        val categories = Categories(emptyList())
        this.emit(categories)
    }

    fun fetchRandomMeal(): Flow<Meals> = flow {
        emit(api.getRandomMeal())
    }.catch {
        Log.d("API", "Get random meal fail!")
    }

    fun fetchMealByName(name: String): Flow<Meals> = flow {
        emit(RetrofitBuilder.getApiInterface().searchMealByName(name))
    }.catch {
        Log.d("API", "Get data fail!")
    }

    fun fetchMealsByFirstLetter(key: String): Flow<Meals> = flow {
        emit(api.getAllMealsByFirstLetter(key))
    }.catch {
        Log.d("API", "Get meals by first letter fail!")
    }

    fun fetchMealById(id: String): Flow<Meals> = flow {
        emit(api.getMealDetailsById(id))
    }.catch {
        Log.d("API", "Get meal by id fail!")
    }

    fun insertMeal(mealModel: MealModel) {
        database.mealDAO().insertMeal(mealModel)
    }

    fun deleteMeal(mealModel: MealModel) {
        database.mealDAO().deleteMeal(mealModel)
    }

    fun getAllMeals(category: String): Flow<List<MealModel>> = flow {
        emit(database.mealDAO().getAllMealsByCategory(category))
    }.catch {
        emit(emptyList())
    }

    fun getListFavoriteMeals(): Flow<List<MealModel>> = flow {
        emit(database.mealDAO().getListFavoriteMeals())
    }.catch {
        emit(emptyList())
    }

    fun getAllCategories(): Flow<List<CategoryModel>> = flow {
        emit(database.categoryDAO().getAllCategories())
    }.catch {
        emit(emptyList())
    }

    fun getRandomCategory(): Flow<String> = flow {
        emit(database.categoryDAO().getRandomCategory() ?: "")
    }.catch {
        emit(Constant.DEFAULT_CATEGORY)
    }

    fun insertListCategories(list: List<CategoryModel>) {
        database.categoryDAO().insertListCategories(list)
    }

}