package com.chocolatecake.indianfood.ui.search
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.chocolatecake.indianfood.R
import com.chocolatecake.indianfood.dataSource.CsvDataSource
import com.chocolatecake.indianfood.dataSource.utils.CsvParser
import com.chocolatecake.indianfood.databinding.FragmentIngredientsSearchBinding
import com.chocolatecake.indianfood.interactor.FindRecipesByNameInteractor
import com.chocolatecake.indianfood.interactor.FindRecipesContainsSpecifiedIngredientInteractor
import com.chocolatecake.indianfood.interactor.GetRandomMealIntractor
import com.chocolatecake.indianfood.interactor.IndianFoodDataSource
import com.chocolatecake.indianfood.model.Recipe
import com.chocolatecake.indianfood.ui.RecipeDetailsFragment
import com.chocolatecake.indianfood.ui.base.BaseFragment
import com.chocolatecake.indianfood.util.Constants.INSTRUCTIONS_TAB_INDEX
import com.chocolatecake.indianfood.util.Constants.RECIPE_TAB_INDEX
import com.chocolatecake.indianfood.util.ItemListener
import com.chocolatecake.indianfood.util.navigateTo
import com.google.android.material.chip.Chip

class IngredientsSearchFragment : BaseFragment<FragmentIngredientsSearchBinding>(), ItemListener {
    private lateinit var dataSource: IndianFoodDataSource
    private lateinit var searchIngredient: FindRecipesContainsSpecifiedIngredientInteractor
    private lateinit var searchRecipes: FindRecipesByNameInteractor
    private lateinit var csvParser: CsvParser
    private var ingredientsList = mutableListOf("oil")
    private var recipeName = "Ragi"
    private lateinit var getRandomMeal : GetRandomMealIntractor
    private val randomRecipesList = mutableListOf<String>()


    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentIngredientsSearchBinding
        get() = FragmentIngredientsSearchBinding::inflate


    override fun setUp() {
        setupDatasource()
        checkIfRecipeOrIngredientToSearch()
        setChipOnClickListener()
    }

    private fun setChipOnClickListener() {
        binding.chipsgroup.chipGroup.setOnClickListener() { view ->
            val chipText = (view.parent as Chip).text.toString()
            binding.searchView.setQuery(chipText, false)
        }
    }

    private fun checkIfRecipeOrIngredientToSearch() {
        when (3.toString()) {
            RECIPE_TAB_INDEX -> {
                searchIngredient(ingredientsList)
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

            val ingredients = searchIngredient.invoke(ingredientsList)

            if (ingredients.isNotEmpty()) {
                setUpAdapter(ingredients)
            } else {
                binding.noDataFound.error.visibility = View.VISIBLE
            }
        } catch (e: IllegalAccessException) {
            showToast(message = e.message.toString())
        }
    }

    private fun getRecipes(searchText: String) {
        try {
            val recipes = searchRecipes.invoke(searchName = searchText)

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
        searchIngredientScreen()
        searchRecipesScreen()
    }
    fun createChip(name: String, context: Context) {
        val chip = Chip(context)
        chip.apply {

            text = name
            chipIcon = ContextCompat.getDrawable(
                context,
                R.drawable.ic_launcher_background
            )
            isChipIconVisible = false
            isCloseIconVisible = true
            isCheckable = true
            isClickable = true
            binding.apply {
                chipsgroup.chipGroup.addView(chip as View)
                chip.setOnCloseIconClickListener {
                    chipsgroup.chipGroup.removeView(it)
                    ingredientsList.remove(it.toString())
                }
            }
        }
    }

    private fun searchIngredientScreen() {

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                ingredientsList.add(query!!)
                recipeName = query
                context?.let { createChip(query, it) }
                return false

            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    private fun searchRecipesScreen() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { getRecipes(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
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

    private fun showToast(message: String) {
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}