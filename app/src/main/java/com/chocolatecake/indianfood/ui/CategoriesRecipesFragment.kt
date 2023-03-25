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


private const val ARG_PARAM1 = "param1"

class CategoriesRecipesFragment : BaseFragment<FragmentCategoriesRecipesBinding>() {

    private var param1: String? = null
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCategoriesRecipesBinding
        get() = FragmentCategoriesRecipesBinding::inflate

    override fun setUp() {

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
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
        TODO("Not yet implemented")
    }


    companion object {
        fun newInstance(param1: String) =
            CategoriesRecipesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}