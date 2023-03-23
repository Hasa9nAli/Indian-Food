package com.chocolatecake.indianfood.interactor

import com.chocolatecake.indianfood.model.Recipe

class GetQuickRecipesInteractor(
   private val dataSource: IndianFoodDataSource,
) {

    operator fun invoke(limit: Int): List<Recipe> {
        return dataSource
            .getAllRecipesData()
            .ifEmpty { throw IllegalAccessException("Internal error occurred") }
            .sortedBy { it.totalTimeInMinutes }
            .take(limit)
    }

}