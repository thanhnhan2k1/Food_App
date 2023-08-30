package com.example.foodappbeta.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("category")
data class CategoryModel(
    @PrimaryKey
    val idCategory: String,
    val strCategory: String?
)
