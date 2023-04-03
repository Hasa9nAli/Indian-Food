package com.chocolatecake.indianfood.interactor

class FindRecipesNameInteractor(private val dataSource: IndianFoodDataSource) {
    fun invoke(
        searchName: String
    ): List<String>  = dataSource.getAllRecipesData()
        .ifEmpty { throw IllegalAccessException("Meal not found") }
        .asSequence()
        .filter { recipe ->
            recipe.name.contains(searchName, ignoreCase = true)
        }
        .map { it.name }
        .toList()
}