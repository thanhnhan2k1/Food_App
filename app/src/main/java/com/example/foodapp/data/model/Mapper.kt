package com.example.foodapp.data.model

import com.example.foodapp.data.entity.*

object Mapper {
    fun Categories.toCategoriesModel(): CategoriesModel {

        return CategoriesModel(categories?.map { category ->
            category.toCategoryModel()
        } ?: emptyList())
    }

    private fun Category.toCategoryModel(): CategoryModel {
        return CategoryModel(idCategory ?: "", strCategory)
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
            strInstructions,
            strCategory
        )
    }

    fun Meals.toMealsModel(): MealsModel {

        return MealsModel(meals?.map {meal ->
            meal.toMealModel()
        } ?: emptyList())
    }

    fun MealModel.toListIngredient(): List<Ingredient>{
        val list = mutableListOf<Ingredient>()
        if(!strIngredient1.isNullOrBlank()){
            val ingredient1 =
                Ingredient(strIngredient = strIngredient1, strMeasure = strMeasure1 ?: "--" )
            list.add(ingredient1)
        }
        if(!strIngredient2.isNullOrBlank()){
            val ingredient2 =
                Ingredient(strIngredient = strIngredient2, strMeasure = strMeasure2 ?: "--")
            list.add(ingredient2)
        }
        if(!strIngredient3.isNullOrBlank()){
            val ingredient3 =
                Ingredient(strIngredient = strIngredient3, strMeasure = strMeasure3 ?: "--")
            list.add(ingredient3)
        }
        if(!strIngredient4.isNullOrBlank()){
            val ingredient4 =
                Ingredient(strIngredient = strIngredient4, strMeasure = strMeasure4 ?: "--")
            list.add(ingredient4)
        }
        if(!strIngredient5.isNullOrBlank()){
            val ingredient5 =
                Ingredient(strIngredient = strIngredient5, strMeasure = strMeasure5 ?: "--")
            list.add(ingredient5)
        }
        if(!strIngredient6.isNullOrBlank()){
            val ingredient6 =
                Ingredient(strIngredient = strIngredient6, strMeasure = strMeasure6 ?: "--")
            list.add(ingredient6)
        }
        if(!strIngredient7.isNullOrBlank()){
            val ingredient7 =
                Ingredient(strIngredient = strIngredient7, strMeasure = strMeasure7 ?: "--")
            list.add(ingredient7)
        }
        if(!strIngredient8.isNullOrBlank()){
            val ingredient8 =
                Ingredient(strIngredient = strIngredient8, strMeasure = strMeasure8 ?: "--")
            list.add(ingredient8)
        }
        if(!strIngredient9.isNullOrBlank()){
            val ingredient9 =
                Ingredient(strIngredient = strIngredient9, strMeasure = strMeasure9 ?: "--")
            list.add(ingredient9)
        }
        if(!strIngredient10.isNullOrBlank()){
            val ingredient10 =
                Ingredient(strIngredient = strIngredient10, strMeasure = strMeasure10 ?: "--")
            list.add(ingredient10)
        }
        if(!strIngredient11.isNullOrBlank()){
            val ingredient11 =
                Ingredient(strIngredient = strIngredient11, strMeasure = strMeasure11 ?: "--")
            list.add(ingredient11)
        }
        if(!strIngredient12.isNullOrBlank()){
            val ingredient12 =
                Ingredient(strIngredient = strIngredient12, strMeasure = strMeasure12 ?: "--")
            list.add(ingredient12)
        }
        if(!strIngredient13.isNullOrBlank()){
            val ingredient13 =
                Ingredient(strIngredient = strIngredient13, strMeasure = strMeasure13 ?: "--")
            list.add(ingredient13)
        }
        if(!strIngredient14.isNullOrBlank()){
            val ingredient14 =
                Ingredient(strIngredient = strIngredient14, strMeasure = strMeasure14 ?: "--")
            list.add(ingredient14)
        }
        if(!strIngredient15.isNullOrBlank()){
            val ingredient15 =
                Ingredient(strIngredient = strIngredient15, strMeasure = strMeasure15 ?: "--")
            list.add(ingredient15)
        }
        if(!strIngredient16.isNullOrBlank()){
            val ingredient16 =
                Ingredient(strIngredient = strIngredient16, strMeasure = strMeasure16 ?: "--")
            list.add(ingredient16)
        }
        if(!strIngredient17.isNullOrBlank()){
            val ingredient17 =
                Ingredient(strIngredient = strIngredient17, strMeasure = strMeasure17 ?: "--")
            list.add(ingredient17)
        }
        if(!strIngredient18.isNullOrBlank()){
            val ingredient18 =
                Ingredient(strIngredient = strIngredient18, strMeasure = strMeasure18 ?: "--")
            list.add(ingredient18)
        }
        if(!strIngredient19.isNullOrBlank()){
            val ingredient19 =
                Ingredient(strIngredient = strIngredient19, strMeasure = strMeasure19 ?: "--")
            list.add(ingredient19)
        }
        if(!strIngredient20.isNullOrBlank()){
            val ingredient20 =
                Ingredient(strIngredient = strIngredient20, strMeasure = strMeasure20 ?: "--")
            list.add(ingredient20)
        }
        return list
    }
}