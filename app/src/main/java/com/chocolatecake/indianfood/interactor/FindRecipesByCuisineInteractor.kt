package com.chocolatecake.indianfood.interactor

import com.chocolatecake.indianfood.model.Recipe

class FindRecipesByCuisineInteractor(private val dataSource: IndianFoodDataSource){
    fun invoke(cuisine: String): List<Recipe> {
        return getRecipesBuyCuisineName(cuisine)
    }

    private fun getRecipesBuyCuisineName(cuisine: String): List<Recipe> {
        return dataSource
            .getAllRecipesData()
            .ifEmpty { throw IllegalStateException("Something went wrong") }
            .filter { recipe -> recipe.cuisine.lowercase().trim() == cuisine.lowercase().trim() }
    }

}