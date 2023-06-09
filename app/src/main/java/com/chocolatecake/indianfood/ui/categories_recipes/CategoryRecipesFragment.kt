package com.chocolatecake.indianfood.ui.categories_recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chocolatecake.indianfood.dataSource.IndianFoodCsvDataSource
import com.chocolatecake.indianfood.databinding.FragmentCategoryRecipesBinding
import com.chocolatecake.indianfood.interactor.GetBreakfastRecipesInteractor
import com.chocolatecake.indianfood.interactor.GetDinnerRecipesInteractor
import com.chocolatecake.indianfood.interactor.GetLunchRecipesInteractor
import com.chocolatecake.indianfood.model.Recipe
import com.chocolatecake.indianfood.ui.base.BaseFragment
import com.chocolatecake.indianfood.ui.recipe_details.DetailsFragment
import com.chocolatecake.indianfood.util.navigateBack
import com.chocolatecake.indianfood.util.navigateTo


class CategoryRecipesFragment : BaseFragment<FragmentCategoryRecipesBinding>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCategoryRecipesBinding
        get() = FragmentCategoryRecipesBinding::inflate

    override fun setUp() {
        binding.appBar.textViewAppBarTitle.text = getMealType()
        setUpRecipesRecyclerView(
            CategoryRecipesAdapter(
                getMealRecipes(getMealType()),
                ::onClickRecipe
            )
        )
    }

    private fun onClickRecipe(recipe: Recipe) {
        val recipeDetailsFragment = DetailsFragment.newInstance(recipe)
        requireActivity().navigateTo(recipeDetailsFragment)
    }

    private fun getMealRecipes(mealType: String) = when (mealType) {
        GetBreakfastRecipesInteractor.BREAKFAST -> {
            GetBreakfastRecipesInteractor(
                IndianFoodCsvDataSource(
                    requireContext()
                )
            ).invoke()
        }

        GetLunchRecipesInteractor.LUNCH -> {
            GetLunchRecipesInteractor(
                IndianFoodCsvDataSource(
                    requireContext()
                )
            ).invoke()
        }

        GetDinnerRecipesInteractor.DINNER -> {
            GetDinnerRecipesInteractor(
                IndianFoodCsvDataSource(
                    requireContext()
                )
            ).invoke()
        }

        else -> {
            throw NoSuchElementException()
        }
    }

    private fun getMealType() = requireArguments().getString(MEAL_TYPE)!!

    override fun addCallBacks() {
        navigateBackToCategoryFragment()
    }

    private fun setUpRecipesRecyclerView(
        categoryRecipesAdapter: CategoryRecipesAdapter,
    ) {
        binding.recyclerViewCategoryRecipes.adapter = categoryRecipesAdapter
    }

    private fun navigateBackToCategoryFragment() {
        binding.appBar.buttonBack.setOnClickListener {
            requireActivity().navigateBack()
        }
    }

    companion object {

        const val MEAL_TYPE = "meal"

        fun newInstance(mealType: String) =
            CategoryRecipesFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_TYPE, mealType)
                }
            }
    }
}