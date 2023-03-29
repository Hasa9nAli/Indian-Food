package com.chocolatecake.indianfood.ui.show_more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chocolatecake.indianfood.R
import com.chocolatecake.indianfood.dataSource.IndianFoodCsvDataSource
import com.chocolatecake.indianfood.dataSource.utils.CsvParser
import com.chocolatecake.indianfood.databinding.FragmentShowMoreBinding
import com.chocolatecake.indianfood.interactor.GetHealthyRecipesInteractor
import com.chocolatecake.indianfood.interactor.GetQuickRecipesInteractor
import com.chocolatecake.indianfood.model.Recipe
import com.chocolatecake.indianfood.ui.base.BaseFragment
import com.chocolatecake.indianfood.util.navigateBack

class ShowMoreFragment : BaseFragment<FragmentShowMoreBinding>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentShowMoreBinding =
        FragmentShowMoreBinding::inflate

    override fun setUp() {
        setUpAdapter()
        binding.appBar.textViewAppBarTitle.text = getCategoryTitle()
    }

    private fun getCategoryTitle() = when (getCategoryType()) {
        GetQuickRecipesInteractor.QUICK_RECIPES_TYPE -> {
            getString(R.string.quick_recipes_category_title)
        }

        GetHealthyRecipesInteractor.HEALTHY_RECIPES_TYPE -> {
            getString(R.string.healthy_recipes_category_title)
        }

        else -> {
            throw IllegalArgumentException("Unknown category type")
        }
    }

    private fun setUpAdapter() {
        binding.recyclerViewCategoryRecipes.adapter =
            ShowMoreAdapter(geRecipesCategoryType(getCategoryType()))
    }

    private fun geRecipesCategoryType(categoryType: String): List<Recipe> {
        val csvData = IndianFoodCsvDataSource(CsvParser(), requireContext())

        return when (categoryType) {
            GetHealthyRecipesInteractor.HEALTHY_RECIPES_TYPE ->
                GetHealthyRecipesInteractor(csvData).invoke()

            GetQuickRecipesInteractor.QUICK_RECIPES_TYPE ->
                GetQuickRecipesInteractor(csvData).invoke()

            else -> {
                throw IllegalArgumentException("Unknown category type")
            }
        }
    }

    private fun getCategoryType() = arguments?.getString(RECIPES_CATEGORY)!!

    override fun addCallBacks() {
        binding.appBar.buttonBack.setOnClickListener {
            requireActivity().navigateBack()
        }
    }

    companion object {
        private const val RECIPES_CATEGORY = "Recipes Category"

        fun newInstance(categoryType: String) =
            ShowMoreFragment().apply {
                arguments = Bundle().apply {
                    putString(RECIPES_CATEGORY, categoryType)
                }
            }
    }
}