
package com.chocolatecake.indianfood.ui.search_recipes
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import com.chocolatecake.indianfood.dataSource.IndianFoodCsvDataSource
import com.chocolatecake.indianfood.dataSource.utils.CsvParser
import com.chocolatecake.indianfood.databinding.FragmentRecipesSearchBinding
import com.chocolatecake.indianfood.interactor.*
import com.chocolatecake.indianfood.model.Recipe
import com.chocolatecake.indianfood.ui.RecipeDetailsFragment
import com.chocolatecake.indianfood.ui.base.BaseFragment
import com.chocolatecake.indianfood.util.navigateTo
import com.google.android.material.chip.Chip

class RecipesSearchFragment : BaseFragment<FragmentRecipesSearchBinding>() {
    private lateinit var dataSource: IndianFoodDataSource
    private lateinit var csvParser: CsvParser
    private lateinit var findRecipesByName: FindRecipesByNameInteractor
    private lateinit var getRandomMeals : GetRandomMealsIntractor
    private var recipeName  = "no data"



    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRecipesSearchBinding
        get() = FragmentRecipesSearchBinding::inflate




    override fun setUp() {
        setupDatasource()
        setSearchOnClickListener()
        checkIfRecipeOrIngredient()

    }

    override fun addCallBacks() {
        onChoiceChip()
    }

    private fun setupDatasource() {
        csvParser = CsvParser()
        dataSource = IndianFoodCsvDataSource(csvParser, requireContext())
        findRecipesByName = FindRecipesByNameInteractor(dataSource)
        getRandomMeals = GetRandomMealsIntractor(dataSource)

    }

    private fun setSearchOnClickListener() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit( query: String?): Boolean {
                        recipeName = query!!
                        getRecipes(query)

                binding.searchView.clearFocus()
                return false

            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    private fun checkIfRecipeOrIngredient() {
        setRandomMeals()
        getRecipes(recipeName)
    }





    private fun getRecipes(searchText: String) {
        try {
            val recipes = findRecipesByName.invoke(searchName = searchText)

            if (recipes.isNotEmpty() ) {
                setUpAdapter(recipes)
                visibilityAndGoneView(searchRecyclerVisibility = View.VISIBLE ,
                    noDataFoundVisibility = View.GONE )  // not tested
            } else {
                visibilityAndGoneView(searchRecyclerVisibility = View.GONE ,
                    noDataFoundVisibility = View.VISIBLE )  // not tested
            }
        } catch (e: IllegalAccessException) {
            showToast(message = e.message.toString())
        }
    }

    private fun visibilityAndGoneView( searchRecyclerVisibility : Int , noDataFoundVisibility : Int ){
        binding.recipeSearchRecyclerView.visibility = searchRecyclerVisibility
        binding.noDataFound.error.visibility = noDataFoundVisibility
    }


    private fun setRandomMeals() {
        try {
            val randomMeals = getRandomMeals.invoke().take(4)
            if ( randomMeals.isNotEmpty()) {
                createChips(randomMeals)
            } else {
                binding.noDataFound.error.visibility = View.VISIBLE
            }
        } catch (e: IllegalAccessException) {
            showToast(message = e.message.toString())
        }

    }


    private fun setUpAdapter(recipe: List<Recipe>) {
        binding.recipeSearchRecyclerView.adapter = RecipesSearchAdapter(recipe, onClickItem = ::onClickRecipe)
    }


    private fun onChoiceChip(){
        binding.chipsgroup.setOnCheckedStateChangeListener {
                group , checkedId ->
            val  chip:Chip = group.findViewById(checkedId[checkedId.lastIndex])

            chip.let {
                recipeName = it.text.toString()
                showToast(message = it.text.toString())
                getRecipes(recipeName)
            }
        }

    }


    private fun createChips(items: List<String> ) {

        for (chipText in items) {
            val chip = Chip(context)
            chip.text = chipText
            chip.isClickable = true
            chip.isCheckable = true
            binding.chipsgroup.addView(chip)
        }
    }


    private fun onClickRecipe(recipe: Recipe) {
        requireActivity().navigateTo(RecipeDetailsFragment.newInstance(recipe))
    }

    companion object {
        private const val RECIPE_TAB_INDEX = "1"

        fun newInstance(index: Int) =
            RecipeDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(RECIPE_TAB_INDEX, index)
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