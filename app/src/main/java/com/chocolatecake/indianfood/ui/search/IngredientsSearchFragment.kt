package com.chocolatecake.indianfood.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.chocolatecake.indianfood.dataSource.CsvDataSource
import com.chocolatecake.indianfood.dataSource.utils.CsvParser
import com.chocolatecake.indianfood.databinding.FragmentIngredientsSearchBinding
import com.chocolatecake.indianfood.interactor.FindRecipesByNameInteractor
import com.chocolatecake.indianfood.interactor.FindRecipesContainsSpecifiedIngredientInteractor
import com.chocolatecake.indianfood.interactor.IndianFoodDataSource
import com.chocolatecake.indianfood.model.Recipe
import com.chocolatecake.indianfood.ui.RecipeDetailsFragment
import com.chocolatecake.indianfood.ui.base.BaseFragment
import com.chocolatecake.indianfood.util.Constants.INSTRUCTIONS_TAB_INDEX
import com.chocolatecake.indianfood.util.Constants.RECIPE_TAB_INDEX
import com.chocolatecake.indianfood.util.ItemListener
import com.chocolatecake.indianfood.util.navigateTo

class IngredientsSearchFragment : BaseFragment<FragmentIngredientsSearchBinding>(), ItemListener {

    private lateinit var dataSource: IndianFoodDataSource
    private lateinit var searchIngredient: FindRecipesContainsSpecifiedIngredientInteractor
    private lateinit var searchRecipes: FindRecipesByNameInteractor
    private lateinit var csvParser: CsvParser
    private var ingredientsList = mutableListOf("oil")
    private var recipeName = "masala"


    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentIngredientsSearchBinding
        get() = FragmentIngredientsSearchBinding::inflate


    override fun setUp() {
        setupDatasource()
        checkIfRecipeOrIngredientToSearch()
    }

    private fun checkIfRecipeOrIngredientToSearch() {
        when (arguments.toString()) {
            RECIPE_TAB_INDEX -> {
                getRecipes()
            }
            INSTRUCTIONS_TAB_INDEX -> {
                getInstructions()
            }
        }
    }


    private fun setupDatasource() {
        csvParser = CsvParser()
        dataSource = CsvDataSource(csvParser, requireContext())
        searchIngredient = FindRecipesContainsSpecifiedIngredientInteractor(dataSource)
        searchRecipes = FindRecipesByNameInteractor(dataSource)

    }

    private fun getInstructions() {
        try {

            var ingredients = searchIngredient.invoke(ingredientsList)

            if (ingredients.isNotEmpty()) {
                setUpAdapter(ingredients)
            } else {
                binding.noDataFound.error.visibility = View.VISIBLE
            }
        } catch (e: IllegalAccessException) {
            showToast(message = e.message.toString())
        }
    }

    private fun getRecipes() {
        try {
            var recipes = searchRecipes.invoke(searchName = recipeName)

            if (recipes.isNotEmpty()) {
                setUpAdapter(recipes)
            } else {
                binding.noDataFound.error.visibility = View.VISIBLE
            }
        } catch (e: IllegalAccessException) {
            showToast(message = e.message.toString())
        }
    }


    private fun setUpAdapter(recipe: List<Recipe>) {
        val searchAdapter = SearchAdapter(recipe, this)
        binding.searchRecyclerView.adapter = searchAdapter
    }


    override fun onClickItem(recipe: Recipe) {
        requireActivity().navigateTo(RecipeDetailsFragment.newInstance(recipe))
    }


    override fun addCallBacks() {
    }


    companion object {
        fun newInstance(index: Int) =
            RecipeDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(RECIPE_TAB_INDEX, index)
                    putInt(INSTRUCTIONS_TAB_INDEX, index)
                }
            }
    }


    fun showToast(message: String) {
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}