package com.example.foodapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foodapp.model.Meal

@Dao
interface MealDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeal(meal: Meal)

    @Update
    fun updateMeal(meal: Meal)

    @Delete
    fun deleteMeal(meal: Meal)

    @Query("select * from meal")
    fun getAllMeals(): List<Meal>

    @Query("select * from meal order by RANDOM() limit 10")
    fun get10RandomMeals(): LiveData<List<Meal>>

    @Query("delete from meal")
    fun deleteAllMeal()

    @Query("select strMeal from meal where idMeal = :id")
    fun getMealById(id: Int): String
}