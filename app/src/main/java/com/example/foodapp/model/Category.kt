package com.example.foodapp.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    @SerialName("idCategory")
    val idCategory: String?,
    @SerialName("strCategory")
    val strCategory: String?,
    @SerialName("strCategoryDescription")
    val strCategoryDescription: String?,
    @SerialName("strCategoryThumb")
    val strCategoryThumb: String?
)