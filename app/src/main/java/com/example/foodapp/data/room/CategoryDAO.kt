package com.example.foodapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.foodapp.data.model.CategoryModel

@Dao
interface CategoryDAO {
    @Insert
    fun insertListCategories(list: List<CategoryModel>)

    @Query("Select * from category")
    fun getAllCategories(): List<CategoryModel>

    @Query("Select strCategory from category limit 1 offset (select count(*) from category)")
    fun getRandomCategory(): String?
}