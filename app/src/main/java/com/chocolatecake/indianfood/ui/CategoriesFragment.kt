package com.chocolatecake.indianfood.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.chocolatecake.indianfood.R
import com.chocolatecake.indianfood.databinding.FragmentCategoriesBinding
import com.chocolatecake.indianfood.interactor.GetBreakfastRecipesInteractor
import com.chocolatecake.indianfood.interactor.GetDinnerRecipesInteractor
import com.chocolatecake.indianfood.interactor.GetLunchRecipesInteractor
import com.chocolatecake.indianfood.ui.base.BaseFragment


class CategoriesFragment : BaseFragment<FragmentCategoriesBinding>() {

    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCategoriesBinding =
        FragmentCategoriesBinding::inflate

    private fun navigateToCategoriesRecipes(mealType: String) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, CategoriesRecipesFragment.newInstance(mealType))
        transaction.commit()
    }


    override fun setUp() {
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

    override fun addCallBacks() {
    }

}

