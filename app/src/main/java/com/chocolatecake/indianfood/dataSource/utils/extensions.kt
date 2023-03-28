package com.chocolatecake.indianfood.dataSource.utils

import com.chocolatecake.indianfood.model.DetailsViews
import com.chocolatecake.indianfood.util.RecipeViewType

fun String.splitByDots(): List<String> = split(".")

fun String.splitBySemicolon(): List<String> = split(";")

fun String.removeEmptyLines(): String = replace("\n", "")

fun String.toDetailsItem(): DetailsViews<Any> {
    return DetailsViews(this, RecipeViewType.ITEM_INGREDIENT)
}