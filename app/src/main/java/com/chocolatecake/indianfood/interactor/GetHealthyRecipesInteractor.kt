package com.chocolatecake.indianfood.interactor

import com.chocolatecake.indianfood.model.Recipe


class GetHealthyRecipesInteractor(
    private val dataSource: IndianFoodDataSource,
) {

    companion object {
        const val HEALTHY = "Healthy recipes"
    }


    operator fun invoke(
        limit: Int
    ): List<Recipe> =
        dataSource
            .getAllRecipesData()
            .ifEmpty { throw IllegalStateException("Something went wrong") }
            .filter { it.name.contains(HEALTHY, ignoreCase = true) }
            .take(limit)
}