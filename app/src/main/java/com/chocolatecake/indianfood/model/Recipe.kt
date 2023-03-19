package com.chocolatecake.indianfood.model


data class Recipe(
    val name: String,
    val ingredients: List<String>,
    val totalTimeInMinutes: Int,
    val cuisine: String,
    val instruction: List<String>,
    val sourceUrl: String,
    val cleanedIngredients: List<String>,
    val imageUrl: String,
)
