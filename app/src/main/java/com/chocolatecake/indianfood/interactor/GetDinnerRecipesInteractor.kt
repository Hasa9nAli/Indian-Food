package com.chocolatecake.indianfood.interactor

import com.chocolatecake.indianfood.model.Recipe

class GetDinnerRecipesInteractor(private val dataSource: IndianFoodDataSource) {
    fun invoke(): List<Recipe> {
        return dataSource.getAllRecipesData()
            .ifEmpty { throw IllegalStateException("Something went wrong") }
            .asSequence()
            .filter { isDinnerRecipe(it) }
            .toList()
    }

    private fun isDinnerRecipe(recipe: Recipe): Boolean {
        return recipe
            .instruction.any {
                it.contains(DINNER, ignoreCase = true)
            }
    }

    companion object{
        const val DINNER = "Dinner"
    }
}