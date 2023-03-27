
package com.chocolatecake.indianfood.ui.search_ingredients
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import com.chocolatecake.indianfood.dataSource.IndianFoodCsvDataSource
import com.chocolatecake.indianfood.dataSource.utils.CsvParser
import com.chocolatecake.indianfood.databinding.FragmentIngredientsSearchBinding
import com.chocolatecake.indianfood.interactor.*
import com.chocolatecake.indianfood.model.Recipe
import com.chocolatecake.indianfood.ui.RecipeDetailsFragment
import com.chocolatecake.indianfood.ui.base.BaseFragment
import com.chocolatecake.indianfood.util.navigateTo
import com.google.android.material.chip.Chip

class IngredientsSearchFragment : BaseFragment<FragmentIngredientsSearchBinding>() {
    private lateinit var dataSource: IndianFoodDataSource
    private lateinit var csvParser: CsvParser

    private lateinit var findRecipesContainsSpecifiedIngredient: FindRecipesContainsSpecifiedIngredientInteractor
    private var ingredientsList = mutableListOf("")

    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentIngredientsSearchBinding
        get() = FragmentIngredientsSearchBinding::inflate

    override fun setUp() {
        setupDatasource()
        setSearchOnClickListener()
        getInstructions(ingredientsList)
    }

    private fun setupDatasource() {
        csvParser = CsvParser()
        dataSource = IndianFoodCsvDataSource(csvParser, requireContext())
        findRecipesContainsSpecifiedIngredient = FindRecipesContainsSpecifiedIngredientInteractor(dataSource)

    }


    private fun setSearchOnClickListener() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit( query: String?): Boolean {
                ingredientsList.add(query!!)
                getInstructions(mutableListOf(query))
                createChip(query)
                binding.searchView.clearFocus()
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    private fun createChip(chipNext: String ) {
        val chip = Chip(context)
        chip.text = chipNext
        chip.isClickable = true
        chip.isCheckable = true
        chip.isChipIconVisible = false
        chip.isCloseIconVisible = true
        binding.chipsgroup.addView(chip)
    }


    private fun getInstructions( ingredients: MutableList<String>) {
        try {
            val ingredients = findRecipesContainsSpecifiedIngredient.invoke(ingredients.distinct())

            if (ingredients.isNotEmpty()){
                setUpAdapter(ingredients)

                visibilityAndGoneView(
                    searchRecyclerVisibility = View.VISIBLE ,
                    noDataFoundVisibility = View.GONE,
                )
            } else {
                visibilityAndGoneView(
                    searchRecyclerVisibility = View.GONE ,
                    noDataFoundVisibility = View.VISIBLE,
                )
            }
        } catch (e: IllegalAccessException) {
            showToast(message = e.message.toString())
        }
    }

    private fun setUpAdapter(recipe: List<Recipe>) {
        binding.ingredientsSearchRecyclerView.adapter =
            IngredientsSearchAdapter(
            recipes = recipe, onClickItem = ::onClickIngredient
        )
    }

    private fun onClickIngredient(recipe: Recipe) {
        requireActivity().navigateTo(RecipeDetailsFragment.newInstance(recipe))
    }

    private fun visibilityAndGoneView(
        searchRecyclerVisibility: Int,
        noDataFoundVisibility: Int,
    ){
        binding.ingredientsSearchRecyclerView.visibility = searchRecyclerVisibility
        binding.noDataFound.error.visibility = noDataFoundVisibility
    }


    override fun addCallBacks(){
        onChoiceChips()
    }

    private fun onChoiceChips(){
        binding.chipsgroup.setOnCheckedStateChangeListener {
                group , checkedId ->

            val  chip:Chip = group.findViewById(checkedId[checkedId.lastIndex])
            chip.let {
                ingredientsList.add(it.text.toString())
                getInstructions(mutableListOf(it.text.toString()))
                // getInstructions(ingredientsList)
                showToast(message = it.text.toString())
            }

            chip.setOnCloseIconClickListener {
                binding.chipsgroup.removeView(it)
                ingredientsList.remove(it.toString())
                getInstructions(ingredientsList)
            }
        }
    }


    companion object {
        private const val INSTRUCTIONS_TAB_INDEX = "3"

        fun newInstance(index: Int) {
            RecipeDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(INSTRUCTIONS_TAB_INDEX, index)
                }
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