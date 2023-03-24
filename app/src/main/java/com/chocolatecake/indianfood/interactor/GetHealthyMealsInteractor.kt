package com.chocolatecake.indianfood.interactor
import com.chocolatecake.indianfood.model.Recipe


class GetHealthyMealsInteractor(
    private val dataSource: IndianFoodDataSource,
){

    companion object {
        const val HEALTHY = "healthy"
    }

    operator fun invoke(): List<Recipe> =
        dataSource
            .getAllRecipesData()
            .ifEmpty { throw IllegalStateException("Something went wrong") }
            .filter { it.name.contains(HEALTHY, ignoreCase = true) }
}