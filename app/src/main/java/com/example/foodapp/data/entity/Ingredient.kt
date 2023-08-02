package com.example.foodapp.data.entity


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ingredient(
    @SerialName("strIngredient")
    val strIngredient: String,
    @SerialName("strMeasure")
    val strMeasure: String
)