package com.example.foodapp.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Categories(
    @SerialName("categories")
    val categories: List<Category>?
)