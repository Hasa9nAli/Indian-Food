package com.chocolatecake.indianfood.ui.search
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import com.chocolatecake.indianfood.dataSource.CsvDataSource
import com.chocolatecake.indianfood.dataSource.utils.CsvParser
import com.chocolatecake.indianfood.databinding.FragmentIngredientsSearchBinding
import com.chocolatecake.indianfood.interactor.*
import com.chocolatecake.indianfood.model.Recipe
import com.chocolatecake.indianfood.ui.RecipeDetailsFragment
import com.chocolatecake.indianfood.ui.base.BaseFragment
import com.chocolatecake.indianfood.util.Constants.INSTRUCTIONS_TAB_INDEX
import com.chocolatecake.indianfood.util.Constants.RECIPE_TAB_INDEX
import com.chocolatecake.indianfood.util.navigateTo
import com.google.android.material.chip.Chip

class IngredientsSearchFragment : BaseFragment<FragmentIngredientsSearchBinding>() {
    private lateinit var dataSource: IndianFoodDataSource
    private lateinit var findRecipesContainsSpecifiedIngredient: FindRecipesContainsSpecifiedIngredientInteractor
    private lateinit var findRecipesByName: FindRecipesByNameInteractor
    private lateinit var getRandomMeals : GetRandomMealsIntractor
    private lateinit var csvParser: CsvParser
    private var ingredientsList = mutableListOf("")
    private var recipeName  = "no data"
    var tap = 3


    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentIngredientsSearchBinding
        get() = FragmentIngredientsSearchBinding::inflate


    override fun setUp() {
        setupDatasource()
        setSearchOnClickListener()
        checkIfRecipeOrIngredient()

    }

    override fun addCallBacks() {
        when (tap.toString()) {
            RECIPE_TAB_INDEX -> {
                onChoiceChip()
            }
            INSTRUCTIONS_TAB_INDEX -> {
                onChoiceChips()
            }
        }
    }


    private fun setupDatasource() {
        csvParser = CsvParser()
        dataSource = CsvDataSource(csvParser, requireContext())
        findRecipesContainsSpecifiedIngredient = FindRecipesContainsSpecifiedIngredientInteractor(dataSource)
        findRecipesByName = FindRecipesByNameInteractor(dataSource)
        getRandomMeals = GetRandomMealsIntractor(dataSource)

    }

    private fun checkIfRecipeOrIngredient() {
        when (tap.toString()) {
            RECIPE_TAB_INDEX -> {
                setRandomMeals()
                getRecipes(recipeName)
            }
            INSTRUCTIONS_TAB_INDEX -> {
                getInstructions(ingredientsList)
            }
        }
    }


    private fun setSearchOnClickListener() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit( query: String?): Boolean {

                when (tap.toString()) {
                    RECIPE_TAB_INDEX -> {
                        recipeName = query!!
                        getRecipes(query)
                    }
                    INSTRUCTIONS_TAB_INDEX -> {
                        ingredientsList.add(query!!)
                        getInstructions(mutableListOf(query))
                        createChip(query)
                    }
                }

                binding.searchView.clearFocus()
                return false

            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }



    private fun getInstructions( ingredients: MutableList<String>) {
        try {
            val ingredients = findRecipesContainsSpecifiedIngredient.invoke(ingredients.distinct())

            if (ingredients.isNotEmpty()) {
                setUpAdapter(ingredients)
                binding.searchRecyclerView.visibility = View.VISIBLE
                binding.noDataFound.error.visibility = View.GONE
            } else {
                binding.noDataFound.error.visibility = View.VISIBLE
                binding.searchRecyclerView.visibility = View.GONE
            }
        } catch (e: IllegalAccessException) {
            showToast(message = e.message.toString())
        }
    }

    private fun getRecipes(searchText: String) {
        try {
            val recipes = findRecipesByName.invoke(searchName = searchText)

            if (recipes.isNotEmpty() ) {
                setUpAdapter(recipes)
                binding.searchRecyclerView.visibility = View.VISIBLE
                binding.noDataFound.error.visibility = View.GONE
            } else {
                binding.noDataFound.error.visibility = View.VISIBLE
                binding.searchRecyclerView.visibility = View.GONE
            }
        } catch (e: IllegalAccessException) {
            showToast(message = e.message.toString())
        }
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
        binding.searchRecyclerView.adapter = SearchAdapter(recipe, onClickItem = ::onClickRecipe)
    }


     private fun onClickRecipe(recipe: Recipe) {
        requireActivity().navigateTo(RecipeDetailsFragment.newInstance(recipe))
    }



    private fun onChoiceChip(){
        binding.chipsgroup.chipGroup.setOnCheckedStateChangeListener {
            group , checkedId ->
            val  chip:Chip = group.findViewById(checkedId[checkedId.lastIndex])

            chip.let {
                recipeName = it.text.toString()
                showToast(message = it.text.toString())
                getRecipes(recipeName)
            }
        }

    }

    private fun onChoiceChips(){
        binding.chipsgroup.chipGroup.setOnCheckedStateChangeListener {
                group , checkedId ->
            val  chip:Chip = group.findViewById(checkedId[checkedId.lastIndex])

            chip.let {
                ingredientsList.add(it.text.toString())
                getInstructions(mutableListOf(it.text.toString()))
               // getInstructions(ingredientsList)
                showToast(message = it.text.toString())
            }
            chip.setOnCloseIconClickListener {
                 binding.chipsgroup.chipGroup.removeView(it)
                 ingredientsList.remove(it.toString())
                 getInstructions(ingredientsList)
                }
        }

    }



    private fun createChips(items: List<String> ) {

        for (chipText in items) {
            val chip = Chip(context)
            chip.text = chipText
            chip.isClickable = true
            chip.isCheckable = true
            binding.chipsgroup.chipGroup.addView(chip)
        }
    }

    private fun createChip(chipNext: String ) {
            val chip = Chip(context)
            chip.text = chipNext
            chip.isClickable = true
            chip.isCheckable = true
            chip.isChipIconVisible = false
            chip.isCloseIconVisible = true
            binding.chipsgroup.chipGroup.addView(chip)
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