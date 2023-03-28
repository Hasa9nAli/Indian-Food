package com.chocolatecake.indianfood.interactor

class GetAllRecipesInteractor(
    private val dataSource: IndianFoodDataSource
) {
    operator fun invoke(): List<String> {
        return dataSource.getAllRecipesData()
            .ifEmpty { throw IllegalStateException("Something went wrong!!") }
            .flatMap {
                it.cleanedIngredients
            }
            .distinct()
    }
}