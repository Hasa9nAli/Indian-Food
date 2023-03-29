package com.chocolatecake.indianfood.model

import com.chocolatecake.indianfood.util.RecipeViewType

data class DetailsViews<T>(val item: T, val type: RecipeViewType)
