package com.chocolatecake.indianfood.interactor

import com.chocolatecake.indianfood.model.Recipe

class FindRecipesByCuisineInteractor(private val dataSource: IndianFoodDataSource){
    fun invoke(cuisine: String): List<Recipe>{
        return getRecipesBuyCuisineName(cuisine)
    }
    fun getRecipesBuyCuisineName(cuisine: String): List<Recipe>{
        return dataSource
            .getAllRecipesData()
            .ifEmpty { throw IllegalAccessException("the Recipe is not found")}
            .filter { recipe -> recipe.cuisine.lowercase().trim() == cuisine.lowercase().trim()  }
    }

}