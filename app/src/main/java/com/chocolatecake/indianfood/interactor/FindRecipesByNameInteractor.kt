package com.chocolatecake.indianfood.interactor
import com.chocolatecake.indianfood.model.Recipe

class FindRecipesByNameInteractor(private val dataSource: IndianFoodDataSource) {
    fun invoke(
        searchName: String
    ): List<Recipe>  = dataSource.getAllRecipesData()
        .ifEmpty { throw IllegalAccessException("Meal not found") }
        .asSequence()
        .filter { recipe ->
            recipe.name.contains(searchName, ignoreCase = true)
        }
        .toList()
}