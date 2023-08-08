package com.example.foodapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.entity.Ingredient
import com.example.foodapp.model.MealModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IngredientViewModel(private val meal: MealModel) : ViewModel() {
    private val _listIngredient = MutableLiveData<List<Ingredient>>()
    val listIngredient: LiveData<List<Ingredient>>
        get() = _listIngredient

    fun toListIngredient() {
        val list = mutableListOf<Ingredient>()
        if(!meal.strIngredient1.isNullOrBlank()){
            val ingredient1 =
                Ingredient(strIngredient = meal.strIngredient1, strMeasure = meal.strMeasure1 ?: "--" )
            list.add(ingredient1)
        }
        if(!meal.strIngredient2.isNullOrBlank()){
            val ingredient2 =
                Ingredient(strIngredient = meal.strIngredient2, strMeasure = meal.strMeasure2 ?: "--")
            list.add(ingredient2)
        }
        if(!meal.strIngredient3.isNullOrBlank()){
            val ingredient3 =
                Ingredient(strIngredient = meal.strIngredient3, strMeasure = meal.strMeasure3 ?: "--")
            list.add(ingredient3)
        }
        if(!meal.strIngredient4.isNullOrBlank()){
            val ingredient4 =
                Ingredient(strIngredient = meal.strIngredient4, strMeasure = meal.strMeasure4 ?: "--")
            list.add(ingredient4)
        }
        if(!meal.strIngredient5.isNullOrBlank()){
            val ingredient5 =
                Ingredient(strIngredient = meal.strIngredient5, strMeasure = meal.strMeasure5 ?: "--")
            list.add(ingredient5)
        }
        if(!meal.strIngredient6.isNullOrBlank()){
            val ingredient6 =
                Ingredient(strIngredient = meal.strIngredient6, strMeasure = meal.strMeasure6 ?: "--")
            list.add(ingredient6)
        }
        if(!meal.strIngredient7.isNullOrBlank()){
            val ingredient7 =
                Ingredient(strIngredient = meal.strIngredient7, strMeasure = meal.strMeasure7 ?: "--")
            list.add(ingredient7)
        }
        if(!meal.strIngredient8.isNullOrBlank()){
            val ingredient8 =
                Ingredient(strIngredient = meal.strIngredient8, strMeasure = meal.strMeasure8 ?: "--")
            list.add(ingredient8)
        }
        if(!meal.strIngredient9.isNullOrBlank()){
            val ingredient9 =
                Ingredient(strIngredient = meal.strIngredient9, strMeasure = meal.strMeasure9 ?: "--")
            list.add(ingredient9)
        }
        if(!meal.strIngredient10.isNullOrBlank()){
            val ingredient10 =
                Ingredient(strIngredient = meal.strIngredient10, strMeasure = meal.strMeasure10 ?: "--")
            list.add(ingredient10)
        }
        if(!meal.strIngredient11.isNullOrBlank()){
            val ingredient11 =
                Ingredient(strIngredient = meal.strIngredient11, strMeasure = meal.strMeasure11 ?: "--")
            list.add(ingredient11)
        }
        if(!meal.strIngredient12.isNullOrBlank()){
            val ingredient12 =
                Ingredient(strIngredient = meal.strIngredient12, strMeasure = meal.strMeasure12 ?: "--")
            list.add(ingredient12)
        }
        if(!meal.strIngredient13.isNullOrBlank()){
            val ingredient13 =
                Ingredient(strIngredient = meal.strIngredient13, strMeasure = meal.strMeasure13 ?: "--")
            list.add(ingredient13)
        }
        if(!meal.strIngredient14.isNullOrBlank()){
            val ingredient14 =
                Ingredient(strIngredient = meal.strIngredient14, strMeasure = meal.strMeasure14 ?: "--")
            list.add(ingredient14)
        }
        if(!meal.strIngredient15.isNullOrBlank()){
            val ingredient15 =
                Ingredient(strIngredient = meal.strIngredient15, strMeasure = meal.strMeasure15 ?: "--")
            list.add(ingredient15)
        }
        if(!meal.strIngredient16.isNullOrBlank()){
            val ingredient16 =
                Ingredient(strIngredient = meal.strIngredient16, strMeasure = meal.strMeasure16 ?: "--")
            list.add(ingredient16)
        }
        if(!meal.strIngredient17.isNullOrBlank()){
            val ingredient17 =
                Ingredient(strIngredient = meal.strIngredient17, strMeasure = meal.strMeasure17 ?: "--")
            list.add(ingredient17)
        }
        if(!meal.strIngredient18.isNullOrBlank()){
            val ingredient18 =
                Ingredient(strIngredient = meal.strIngredient18, strMeasure = meal.strMeasure18 ?: "--")
            list.add(ingredient18)
        }
        if(!meal.strIngredient19.isNullOrBlank()){
            val ingredient19 =
                Ingredient(strIngredient = meal.strIngredient19, strMeasure = meal.strMeasure19 ?: "--")
            list.add(ingredient19)
        }
        if(!meal.strIngredient20.isNullOrBlank()){
            val ingredient20 =
                Ingredient(strIngredient = meal.strIngredient20, strMeasure = meal.strMeasure20 ?: "--")
            list.add(ingredient20)
        }
        viewModelScope.launch(Dispatchers.IO){
            _listIngredient.postValue(list)
        }
    }

}