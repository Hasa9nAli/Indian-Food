
package com.chocolatecake.indianfood.ui.search_recipes
import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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


class RecipesSearchFragment : BaseFragment<FragmentRecipesSearchBinding>() ,
    AdapterView.OnItemClickListener {
    private lateinit var dataSource: IndianFoodDataSource
    private lateinit var csvParser: CsvParser

    private lateinit var findRecipesByNameIngredient: FindRecipesByNameInteractor
    private lateinit var recipes: List<String>
    private var searchRecipes :String = ""
    private lateinit var recipesAdapter: RecipesSearchAdapter


    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRecipesSearchBinding
        get() = FragmentRecipesSearchBinding::inflate

    override fun setUp() {
        setupDatasource()
        setUpAdapter(findRecipesByNameIngredient.invoke(searchRecipes))
        setUpAutoCompleteTextView()
        setSearchResult(searchRecipes)
    }

    private fun setupDatasource() {
        csvParser = CsvParser()
        dataSource = IndianFoodCsvDataSource(csvParser, requireContext())
        recipes = FindRecipesNameInteractor(dataSource).invoke(searchName = searchRecipes)
        findRecipesByNameIngredient = FindRecipesByNameInteractor(dataSource)
    }

    private fun setUpAdapter(recipes: List<Recipe>) {
        recipesAdapter = RecipesSearchAdapter(recipes, ::onClickRecipe)
        binding.recyclerViewRecipesResult.adapter = recipesAdapter
    }

    private fun onClickRecipe(recipe: Recipe) {
        requireActivity().navigateTo(RecipeDetailsFragment.newInstance(recipe))
    }

    private fun setUpAutoCompleteTextView() {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_dropdown_item_1line,
            recipes
        )
        binding.searchView.setAdapter(
            adapter
        )
        binding.searchView.onItemClickListener = this
    }

    private fun setSearchResult(recipe: String) {
        val searchResult = findRecipesByNameIngredient.invoke(recipe)
        updateRecyclerViewState(searchResult)
    }

    private fun updateRecyclerViewState(searchResult: List<Recipe>) {
        if (searchResult.isNotEmpty()) {
            setViewsVisibility(
                searchRecyclerVisibility = View.VISIBLE,
                noDataFoundVisibility = View.GONE,
            )
            recipesAdapter.setData(searchResult)
        } else {
            setViewsVisibility(
                searchRecyclerVisibility = View.GONE,
                noDataFoundVisibility = View.VISIBLE,
            )
        }
    }

    private fun setViewsVisibility(
        searchRecyclerVisibility: Int,
        noDataFoundVisibility: Int,
    ) {
        binding.recyclerViewRecipesResult.visibility = searchRecyclerVisibility
        binding.noDataFound.error.visibility = noDataFoundVisibility
    }


    override fun addCallBacks() {
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        searchRecipes = parent!!.getItemAtPosition(position).toString()
        binding.searchView.text.clear()
        setSearchResult(searchRecipes)
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

    private fun showToast(message: String){
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

}