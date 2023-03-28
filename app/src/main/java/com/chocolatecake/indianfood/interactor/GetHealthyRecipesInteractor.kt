package com.chocolatecake.indianfood.interactor

import com.chocolatecake.indianfood.model.Recipe


class GetHealthyRecipesInteractor(
    private val dataSource: IndianFoodDataSource,
) {

    companion object {
        const val HEALTHY_RECIPES_TYPE = "Healthy"
    }

    operator fun invoke(limit: Int? = null): List<Recipe> {

        var recipesCount: Int

        return dataSource
            .getAllRecipesData()
            .apply { recipesCount = size }
            .ifEmpty { throw IllegalStateException("Something went wrong") }
            .filter { it.name.contains(HEALTHY_RECIPES_TYPE, ignoreCase = true) }
            .shuffled()
            .take(limit ?: recipesCount)
    }
}