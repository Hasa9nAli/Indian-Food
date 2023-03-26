package com.chocolatecake.indianfood.interactor

import com.chocolatecake.indianfood.model.Recipe


class GetHealthyRecipesInteractor(
    private val dataSource: IndianFoodDataSource,
) {

    companion object {
        const val HEALTHY_TYPE = "Healthy recipes"
        const val HEALTHY = "Healthy"
    }


    operator fun invoke(limit: Int? = null): List<Recipe> {

        var recipesCount: Int

        return dataSource
            .getAllRecipesData()
            .apply { recipesCount = size }
            .ifEmpty { throw IllegalStateException("Something went wrong") }
            .filter { it.name.contains(HEALTHY, ignoreCase = true) }
            .take(limit ?: recipesCount)
    }
}