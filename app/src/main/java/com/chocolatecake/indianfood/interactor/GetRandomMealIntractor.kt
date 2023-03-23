package com.chocolatecake.indianfood.interactor

import com.chocolatecake.indianfood.model.Recipe

class GetRandomMealIntractor(
    private val dataSource: IndianFoodDataSource
) {
    fun invoke(): Recipe =
             dataSource.getAllRecipesData()
            .ifEmpty{ throw IllegalStateException("Something went wrong") }
            .random()

}