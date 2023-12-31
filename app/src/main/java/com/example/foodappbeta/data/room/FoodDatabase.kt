package com.example.foodappbeta.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodappbeta.data.model.CategoryModel
import com.example.foodappbeta.data.model.MealModel

@Database(entities = [MealModel::class, CategoryModel::class], version = 2, exportSchema = false)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun mealDAO(): MealDAO
    abstract fun categoryDAO(): CategoryDAO

    companion object{
        @Volatile
        private var INSTANCE: FoodDatabase? = null
        fun getDatabase(context: Context): FoodDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FoodDatabase::class.java,
                    "food_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}