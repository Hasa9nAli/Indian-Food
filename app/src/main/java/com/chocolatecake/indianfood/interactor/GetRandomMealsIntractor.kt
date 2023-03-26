package com.chocolatecake.indianfood.interactor

import com.chocolatecake.indianfood.model.Recipe

class GetRandomMealsIntractor(
    private val dataSource: IndianFoodDataSource
) {
    fun invoke(): List<String> =
        dataSource.getAllRecipesData()
            .ifEmpty{ throw IllegalStateException("Something went wrong") }
            .map { it.name.substring(it.name.length/2) }
            .shuffled()
            .distinct()
}