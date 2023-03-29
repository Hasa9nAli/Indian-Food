package com.chocolatecake.indianfood.interactor

class FindIngredientsInteractor(
    private val dataSource: IndianFoodDataSource,
) {
    operator fun invoke(input: String, limit: Int): List<String> {
        return dataSource
            .getAllRecipesData()
            .ifEmpty { throw IllegalAccessException("Something went wrong") }
            .flatMap { it.cleanedIngredients }
            .filter { it.contains(input, ignoreCase = true) }
            .distinct()
            .take(limit)
    }
}