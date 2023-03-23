package com.chocolatecake.indianfood.interactor

import com.chocolatecake.indianfood.model.Recipe

class FindRecipesByAnyIngredientsInteractor(
    private val dataSource: IndianFoodDataSource
){
    operator fun invoke(ingredientNames: List<String>): List<Recipe> {

        ingredientNames.ifEmpty { return emptyList() }
        return findAllRecipesByIngredients(ingredientNames)
    }

      private fun ignoreTheCaseOfTheLettersIngredient(ingredientNames: List<String>): List<String> {
            return ingredientNames.map { it.lowercase() }
        }
    private fun ignoreWhitespaceOfTheIngredient(ingredientNames: List<String>): List<String> {
        return ingredientNames.map { it.trim() }
    }

    private fun findAllRecipesByIngredients(ingredientNames: List<String>): List<Recipe> {
        return dataSource.getAllRecipesData().filter { recipe ->
            val cleanedIngredientswithoutWhitespace = ignoreWhitespaceOfTheIngredient(
                ignoreTheCaseOfTheLettersIngredient(recipe.cleanedIngredients)
            )
            ingredientNames.any { ingredient -> cleanedIngredientswithoutWhitespace.contains(ingredient) }
        }
    }

}


