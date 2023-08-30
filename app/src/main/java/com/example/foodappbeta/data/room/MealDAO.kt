package com.example.foodappbeta.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foodappbeta.data.model.MealModel

@Dao
interface MealDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeal(meal: MealModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListMeals(list: List<MealModel>)

    @Update
    fun updateMeal(meal: MealModel)

    @Delete
    fun deleteMeal(meal: MealModel)

    @Query("select * from meal where strCategory = :category")
    fun getAllMealsByCategory(category: String): List<MealModel>

    @Query("select * from meal where isLike = 1")
    fun getListFavoriteMeals(): List<MealModel>

    @Query("select * from meal order by RANDOM() limit 10")
    fun get10RandomMeals(): LiveData<List<MealModel>>

    @Query("delete from meal")
    fun deleteAllMeal()

    @Query("select * from meal where idMeal = :id")
    fun getMealById(id: String): MealModel?
}