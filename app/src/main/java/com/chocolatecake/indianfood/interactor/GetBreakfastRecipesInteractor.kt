package com.chocolatecake.indianfood.interactor

import com.chocolatecake.indianfood.model.Recipe

class GetBreakfastRecipesInteractor(private val dataSource: IndianFoodDataSource) {

    fun invoke(): List<Recipe> {
        return dataSource.getAllRecipesData()
            .ifEmpty { throw IllegalStateException("Something went wrong") }
            .filter { isBreakfastRecipe(it) }
    }

    private fun isBreakfastRecipe(recipe: Recipe): Boolean {
        return recipe
            .instruction.any {
                it.contains(BREAKFAST, ignoreCase = true)
            }
    }

    companion object{
        const val BREAKFAST = "Breakfast"
    }

}