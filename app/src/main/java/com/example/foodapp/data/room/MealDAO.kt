package com.example.foodapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foodapp.model.MealModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeal(meal: MealModel)

    @Update
    fun updateMeal(meal: MealModel)

    @Delete
    fun deleteMeal(meal: MealModel)

    @Query("select * from meal")
    fun getAllMeals(): Flow<List<MealModel>>

    @Query("select * from meal order by RANDOM() limit 10")
    fun get10RandomMeals(): LiveData<List<MealModel>>

    @Query("delete from meal")
    fun deleteAllMeal()

    @Query("select strMeal from meal where idMeal = :id")
    fun getMealById(id: Int): String
}