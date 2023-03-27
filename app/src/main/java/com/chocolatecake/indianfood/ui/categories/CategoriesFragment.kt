package com.chocolatecake.indianfood.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import com.chocolatecake.indianfood.databinding.FragmentCategoriesBinding
import com.chocolatecake.indianfood.interactor.GetBreakfastRecipesInteractor
import com.chocolatecake.indianfood.interactor.GetDinnerRecipesInteractor
import com.chocolatecake.indianfood.interactor.GetLunchRecipesInteractor
import com.chocolatecake.indianfood.ui.base.BaseFragment
import com.chocolatecake.indianfood.ui.categories_recipes.CategoryRecipesFragment
import com.chocolatecake.indianfood.util.navigateTo


class CategoriesFragment : BaseFragment<FragmentCategoriesBinding>() {

    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCategoriesBinding =
        FragmentCategoriesBinding::inflate

    private fun navigateToCategoriesRecipes(mealType: String) {
        requireActivity().navigateTo(CategoryRecipesFragment.newInstance(mealType))
    }

    override fun setUp() {
    }

    override fun addCallBacks() {
        binding.cardViewBreakfast.setOnClickListener {
            navigateToCategoriesRecipes(GetBreakfastRecipesInteractor.BREAKFAST)
        }
        binding.cardViewLunch.setOnClickListener {
            navigateToCategoriesRecipes(GetLunchRecipesInteractor.LUNCH)
        }
        binding.cardViewDinner.setOnClickListener {
            navigateToCategoriesRecipes(GetDinnerRecipesInteractor.DINNER)
        }
    }

}

