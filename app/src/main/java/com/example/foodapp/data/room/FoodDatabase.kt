package com.example.foodapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodapp.model.Meal

@Database(entities = [Meal::class], version = 15, exportSchema = false)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun mealDAO(): MealDAO
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