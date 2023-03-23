package com.chocolatecake.indianfood.interactor

class FindIngredientsInteractor(
    private val dataSource: IndianFoodDataSource,
) {

    operator fun invoke(item : String , limit: Int): List<String> {
        return dataSource
            .getAllRecipesData()
            .ifEmpty { throw IllegalAccessException("Something went wrong") }
            .map {
                it.ingredients
                    .find { ingredient -> ingredient.contains(item, ignoreCase = true) }.toString()
            }.take(limit)
    }
}