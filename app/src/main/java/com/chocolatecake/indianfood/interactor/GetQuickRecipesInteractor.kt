package com.chocolatecake.indianfood.interactor

import com.chocolatecake.indianfood.model.Recipe

class GetQuickRecipesInteractor(
   private val dataSource: IndianFoodDataSource,
) {

    operator fun invoke(limit: Int? = null): List<Recipe> {

        var recipesCount: Int

        return dataSource
            .getAllRecipesData()
            .apply { recipesCount = size }
            .ifEmpty { throw IllegalAccessException("Internal error occurred") }
            .asSequence()
            .sortedBy { it.totalTimeInMinutes }
            .take(limit ?: recipesCount)
            .toList()
    }

    companion object {
        const val QUICK_RECIPES_TYPE = "Quick recipes"
    }
}