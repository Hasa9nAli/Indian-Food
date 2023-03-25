package com.chocolatecake.indianfood.model

import android.os.Parcelable
import com.chocolatecake.indianfood.util.HomeItemType
import kotlinx.parcelize.Parcelize


@Parcelize
data class Recipe(
    val name: String,
    val ingredients: List<String>,
    val totalTimeInMinutes: Int,
    val cuisine: String,
    val instruction: List<String>,
    val sourceUrl: String,
    val cleanedIngredients: List<String>,
    val imageUrl: String,
) : Parcelable

fun Recipe.toHomeItem(): HomeItem<Any> {
    return HomeItem(this, HomeItemType.RECIPE)
}
