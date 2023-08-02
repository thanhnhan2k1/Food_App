package com.example.foodapp.model

import com.example.foodapp.data.entity.Category

data class CategoryModel (val idCategory: String?, val strCategory: String?)

fun Category.toCategoryModel(): CategoryModel{
    return CategoryModel(idCategory, strCategory)
}