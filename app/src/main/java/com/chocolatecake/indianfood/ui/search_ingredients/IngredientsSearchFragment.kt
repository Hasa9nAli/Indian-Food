package com.chocolatecake.indianfood.ui.search_ingredients

import android.R
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import com.chocolatecake.indianfood.dataSource.IndianFoodCsvDataSource
import com.chocolatecake.indianfood.dataSource.utils.CsvParser
import com.chocolatecake.indianfood.databinding.FragmentIngredientsSearchBinding
import com.chocolatecake.indianfood.interactor.FindRecipesContainsSpecifiedIngredientInteractor
import com.chocolatecake.indianfood.interactor.GetAllIngredientsInteractor
import com.chocolatecake.indianfood.interactor.IndianFoodDataSource
import com.chocolatecake.indianfood.model.Recipe
import com.chocolatecake.indianfood.ui.RecipeDetailsFragment
import com.chocolatecake.indianfood.ui.base.BaseFragment
import com.chocolatecake.indianfood.util.navigateTo
import com.google.android.material.chip.Chip

class IngredientsSearchFragment : BaseFragment<FragmentIngredientsSearchBinding>(),
    OnItemClickListener {
    private lateinit var dataSource: IndianFoodDataSource
    private lateinit var csvParser: CsvParser
    private lateinit var ingredients: List<String>
    private var searchIngredients = mutableListOf<String>()
    private lateinit var ingredientsAdapter: IngredientsSearchAdapter
    private lateinit var findRecipesContainsSpecifiedIngredient: FindRecipesContainsSpecifiedIngredientInteractor

    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentIngredientsSearchBinding
        get() = FragmentIngredientsSearchBinding::inflate


    override fun setUp() {
        setupDatasource()
        setUpAdapter(findRecipesContainsSpecifiedIngredient.invoke(emptyList()))
        setUpAutoCompleteTextView()
        setSearchResult(searchIngredients)
    }

    private fun setUpAutoCompleteTextView() {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_dropdown_item_1line,
            ingredients
        )
        binding.searchView.setAdapter(
            adapter
        )
        binding.searchView.onItemClickListener = this
        binding.searchView.setOnEditorActionListener { v, actionId, event ->
            actionId == EditorInfo.IME_ACTION_SEARCH || event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER
        }
    }

    private fun setupDatasource() {
        csvParser = CsvParser()
        dataSource = IndianFoodCsvDataSource(csvParser, requireContext())
        ingredients = GetAllIngredientsInteractor(dataSource).invoke().distinct()
        findRecipesContainsSpecifiedIngredient =
            FindRecipesContainsSpecifiedIngredientInteractor(dataSource)
    }

    private fun createChip(ingredient: String, index: Int) {
        val chip = Chip(requireContext())
        chip.text = ingredient
        chip.isChipIconVisible = false
        chip.isCloseIconVisible = true
        onClickCloseChip(chip, index)
        binding.chipGroupIngredients.addView(chip)
    }

    private fun onClickCloseChip(chip: Chip, index: Int) {
        chip.setOnCloseIconClickListener {
            binding.chipGroupIngredients.removeView(chip)
            searchIngredients.removeAt(index)
            setSearchResult(searchIngredients)
        }
    }

    private fun setSearchResult(ingredients: MutableList<String>) {
        val searchResult = findRecipesContainsSpecifiedIngredient.invoke(ingredients)
        updateRecyclerViewState(searchResult)
    }

    private fun updateRecyclerViewState(searchResult: List<Recipe>) {
        if (searchResult.isNotEmpty()) {
            setViewsVisibility(
                searchRecyclerVisibility = View.VISIBLE,
                noDataFoundVisibility = View.GONE,
            )
            ingredientsAdapter.setData(searchResult)
        } else {
            setViewsVisibility(
                searchRecyclerVisibility = View.GONE,
                noDataFoundVisibility = View.VISIBLE,
            )
        }
    }

    private fun setUpAdapter(recipes: List<Recipe>) {
        ingredientsAdapter = IngredientsSearchAdapter(recipes, ::onClickIngredient)
        binding.recyclerViewIngredientsResult.adapter = ingredientsAdapter
    }

    private fun onClickIngredient(recipe: Recipe) {
        requireActivity().navigateTo(RecipeDetailsFragment.newInstance(recipe))
    }

    private fun setViewsVisibility(
        searchRecyclerVisibility: Int,
        noDataFoundVisibility: Int,
    ) {
        binding.recyclerViewIngredientsResult.visibility = searchRecyclerVisibility
        binding.noDataFound.error.visibility = noDataFoundVisibility
    }

    override fun addCallBacks() {
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

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        searchIngredients.add(parent!!.getItemAtPosition(position).toString())
        binding.searchView.text.clear()
        setSearchResult(searchIngredients)
        createChip(parent.getItemAtPosition(position).toString(), position)
    }
}