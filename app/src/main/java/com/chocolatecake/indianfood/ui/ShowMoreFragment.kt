package com.chocolatecake.indianfood.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chocolatecake.indianfood.dataSource.CsvDataSource
import com.chocolatecake.indianfood.dataSource.utils.CsvParser
import com.chocolatecake.indianfood.databinding.FragmentShowMoreBinding
import com.chocolatecake.indianfood.interactor.GetHealthyRecipesInteractor
import com.chocolatecake.indianfood.interactor.GetQuickRecipesInteractor

class ShowMoreFragment : BaseFragment<FragmentShowMoreBinding>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentShowMoreBinding =
        FragmentShowMoreBinding::inflate


    override fun setUp() {
        setUpAdapter(getCategoryType())
        binding.title.text = getCategoryType()
    }

    private fun setUpAdapter(categoryType: String) {
        val csvData = CsvDataSource(CsvParser(), requireContext())
        binding.mealsGrid.adapter = when (categoryType) {
            GetHealthyRecipesInteractor.HEALTHY_TYPE -> ShowMoreAdapter(
                GetHealthyRecipesInteractor(
                    csvData
                ).invoke()
            )

            GetQuickRecipesInteractor.QUICK_RECIPES_TYPE -> ShowMoreAdapter(
                GetQuickRecipesInteractor(
                    csvData
                ).invoke()
            )

            else -> {
                throw IllegalArgumentException()
            }
        }
    }

    private fun getCategoryType() = arguments?.getString(RECIPES_CATEGORY)!!

    override fun addCallBacks() {
        binding.apply {
            arrowBack.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
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