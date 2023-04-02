
package com.chocolatecake.indianfood.ui.search_recipes
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.chocolatecake.indianfood.dataSource.IndianFoodCsvDataSource
import com.chocolatecake.indianfood.databinding.FragmentRecipesSearchBinding
import com.chocolatecake.indianfood.interactor.FindRecipesByNameInteractor
import com.chocolatecake.indianfood.interactor.IndianFoodDataSource
import com.chocolatecake.indianfood.model.Recipe
import com.chocolatecake.indianfood.ui.base.BaseFragment
import com.chocolatecake.indianfood.ui.recipe_details.DetailsFragment
import com.chocolatecake.indianfood.util.navigateTo


class RecipesSearchFragment : BaseFragment<FragmentRecipesSearchBinding>() {
    private lateinit var dataSource: IndianFoodDataSource
    private lateinit var findRecipesByNameIngredient: FindRecipesByNameInteractor
    private var searchRecipes: String = "NOT_HAVE_DATA"
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
        dataSource = IndianFoodCsvDataSource(requireContext())
        findRecipesByNameIngredient = FindRecipesByNameInteractor(dataSource)
    }

    private fun setUpAdapter(recipes: List<Recipe>) {
        recipesAdapter = RecipesSearchAdapter(recipes, ::onClickRecipe)
        binding.recyclerViewRecipesResult.adapter = recipesAdapter
    }

    private fun onClickRecipe(recipe: Recipe) {
        requireActivity().navigateTo(DetailsFragment.newInstance(recipe))
    }

    private fun setUpAutoCompleteTextView() {
        binding.searchView.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setSearchResult(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {}
            }
        )
        binding.searchView.setOnEditorActionListener { v, actionId, event ->
            actionId == EditorInfo.IME_ACTION_SEARCH || event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER
        }

    }


    private fun setSearchResult(recipe: String) {
        val searchResult = findRecipesByNameIngredient.invoke(recipe)
        updateRecyclerViewState(searchResult )
    }

    private fun updateRecyclerViewState(searchResult: List<Recipe>) {
        if (searchResult.isNotEmpty() || searchRecipes != "NOT_HAVE_DATA") {
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


    companion object {
        private const val RECIPE_TAB_INDEX = "1"

        fun newInstance(index: Int) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(RECIPE_TAB_INDEX, index)
                }
            }
    }
}