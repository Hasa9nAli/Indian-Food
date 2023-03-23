package com.chocolatecake.indianfood.interactor

import com.chocolatecake.indianfood.model.Recipe


class FindRecipesContainsSpecifiedIngredientInteractor(
    private val dataSource: IndianFoodDataSource
) {
    operator fun invoke(ingredientNames: List<String>): List<Recipe> {
        return dataSource.getAllRecipesData()
            .ifEmpty { throw IllegalStateException("Something went wrong!!") }
            .filter { it.cleanedIngredients.containsAllIgnoreCase(ingredientNames) }
    }

    private fun List<String>.containsAllIgnoreCase(ingredientNames: List<String>) =
        map { it.lowercase() }
            .containsAll(ingredientNames.map { it.lowercase() })


}

