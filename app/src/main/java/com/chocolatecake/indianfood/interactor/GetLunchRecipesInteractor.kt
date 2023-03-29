package com.chocolatecake.indianfood.interactor

import com.chocolatecake.indianfood.model.Recipe

class GetLunchRecipesInteractor(private val dataSource: IndianFoodDataSource) {
    fun invoke(): List<Recipe> {
        return dataSource.getAllRecipesData()
            .ifEmpty { throw IllegalStateException("Something went wrong") }
            .filter { isLunchRecipe(it) }
    }

    private fun isLunchRecipe(recipe: Recipe): Boolean {
        return recipe
            .instruction.any {
                it.contains(LUNCH, ignoreCase = true)
            }
    }
    companion object{
        const val LUNCH = "Lunch"
    }

}