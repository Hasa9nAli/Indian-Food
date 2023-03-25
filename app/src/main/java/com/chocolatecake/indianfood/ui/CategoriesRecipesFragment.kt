package com.chocolatecake.indianfood.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chocolatecake.indianfood.dataSource.CsvDataSource
import com.chocolatecake.indianfood.dataSource.utils.CsvParser
import com.chocolatecake.indianfood.databinding.FragmentCategoriesRecipesBinding
import com.chocolatecake.indianfood.interactor.GetBreakfastRecipesInteractor
import com.chocolatecake.indianfood.interactor.GetDinnerRecipesInteractor
import com.chocolatecake.indianfood.interactor.GetLunchRecipesInteractor
import com.chocolatecake.indianfood.model.Recipe
import com.chocolatecake.indianfood.ui.base.BaseFragment


class CategoriesRecipesFragment : BaseFragment<FragmentCategoriesRecipesBinding>() {

    private var param1: String? = null
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCategoriesRecipesBinding
        get() = FragmentCategoriesRecipesBinding::inflate

    override fun setUp() {

        arguments?.let {
            param1 = it.getString(MEAL_TYPE)
        }


        var recipe: List<Recipe>? = when (param1) {
            GetBreakfastRecipesInteractor.BREAKFAST ->
                GetBreakfastRecipesInteractor(
                    CsvDataSource(
                        CsvParser(), requireContext()
                    )
                ).invoke()

            GetLunchRecipesInteractor.LUNCH ->
                GetLunchRecipesInteractor(
                    CsvDataSource(
                        CsvParser(), requireContext()
                    )
                ).invoke()

            GetDinnerRecipesInteractor.DINNER ->
                GetDinnerRecipesInteractor(
                    CsvDataSource(
                        CsvParser(), requireContext()
                    )
                ).invoke()

            else -> {
                throw NoSuchElementException()
            }
        }
    }

    override fun addCallBacks() {
    }


    companion object {

        const val MEAL_TYPE = "meal"

        fun newInstance(mealType: String) =
            CategoriesRecipesFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_TYPE, mealType)
                }
            }
    }
}