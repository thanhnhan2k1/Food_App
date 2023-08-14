package com.example.foodapp.model

import com.example.foodapp.data.entity.Categories
import com.example.foodapp.data.entity.Category
import com.example.foodapp.data.entity.Meal
import com.example.foodapp.data.entity.Meals

object Mapper {
    fun Categories.toCategoriesModel(): CategoriesModel {

        return CategoriesModel(categories?.map { category ->
            category.toCategoryModel()
        } ?: emptyList())
    }

    private fun Category.toCategoryModel(): CategoryModel {
        return CategoryModel(idCategory, strCategory)
    }

    private fun Meal.toMealModel(): MealModel {
        return MealModel(
            idMeal,
            strMeal,
            strIngredient1,
            strIngredient2,
            strIngredient3,
            strIngredient4,
            strIngredient5,
            strIngredient6,
            strIngredient7,
            strIngredient8,
            strIngredient9,
            strIngredient10,
            strIngredient11,
            strIngredient12,
            strIngredient13,
            strIngredient14,
            strIngredient15,
            strIngredient16,
            strIngredient17,
            strIngredient18,
            strIngredient19,
            strIngredient20,
            strMeasure1,
            strMeasure2,
            strMeasure3,
            strMeasure4,
            strMeasure5,
            strMeasure6,
            strMeasure7,
            strMeasure8,
            strMeasure9,
            strMeasure10,
            strMeasure11,
            strMeasure12,
            strMeasure13,
            strMeasure14,
            strMeasure15,
            strMeasure16,
            strMeasure17,
            strMeasure18,
            strMeasure19,
            strMeasure20,
            strMealThumb,
            strInstructions
        )
    }

    fun Meals.toMealsModel(): MealsModel {

        return MealsModel(meals?.map {meal ->
            meal.toMealModel()
        } ?: emptyList())
    }
}