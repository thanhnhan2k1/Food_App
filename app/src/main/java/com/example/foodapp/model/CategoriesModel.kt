package com.example.foodapp.model

import com.example.foodapp.data.entity.Categories

data class CategoriesModel (val categories: List<CategoryModel>?)
fun Categories.toCatogoriesModel(): CategoriesModel{
    val list = mutableListOf<CategoryModel>()
    if (categories != null) {
        for(i in categories){
            list.add(i.toCategoryModel())
        }
    }
    return CategoriesModel(list)
}