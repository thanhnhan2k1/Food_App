package com.example.foodapp.model

import com.example.foodapp.data.entity.Meals

data class MealsModel(val meals: List<MealModel>)
fun Meals.toMealsModel(): MealsModel{
    val list = mutableListOf<MealModel>()
    if (meals != null) {
        for(i in meals){
            list.add(i.toMealModel())
        }
    }
    return MealsModel(list)
}