package com.chocolatecake.indianfood.interactor

import com.chocolatecake.indianfood.model.Recipe

interface IndianFoodDataSource {
    fun getAllRecipesData(): List<Recipe>
}