package com.example.foodappbeta.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.foodappbeta.data.model.CategoryModel

@Dao
interface CategoryDAO {
    @Insert
    fun insertListCategories(list: List<CategoryModel>)

    @Query("Select * from category")
    fun getAllCategories(): List<CategoryModel>

    @Query("Select strCategory from category ORDER BY RANDOM() limit 1")
    fun getRandomCategory(): String?
}