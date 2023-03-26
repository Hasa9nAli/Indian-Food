package com.chocolatecake.indianfood.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chocolatecake.indianfood.ShowMoreAdapter
import com.chocolatecake.indianfood.dataSource.CsvDataSource
import com.chocolatecake.indianfood.dataSource.utils.CsvParser
import com.chocolatecake.indianfood.databinding.ShowMoreBinding
import com.chocolatecake.indianfood.interactor.GetHealthyMealsInteractor
import com.chocolatecake.indianfood.interactor.GetQuickRecipesInteractor
import com.chocolatecake.indianfood.databinding.FragmentShowMoreBinding

class ShowMoreFragment : BaseFragment<FragmentShowMoreBinding>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentShowMoreBinding =
        FragmentShowMoreBinding::inflate

    private var titleCategory: String? = null

    override fun setUp() {
        lateinit var adapter: ShowMoreAdapter
        val csvData = CsvDataSource(CsvParser(), requireContext())
        val title: String = requireArguments().getString(RECIPES_CATEGORY)!!

        Log.d("TAG", "setUp: $title")

        adapter = when (title) {
            GetHealthyMealsInteractor.HEALTHY -> ShowMoreAdapter(GetHealthyMealsInteractor(csvData).invoke())

            GetQuickRecipesInteractor.QUICK_RECIPES -> ShowMoreAdapter(
                GetQuickRecipesInteractor(
                    csvData
                ).invoke()
            )

            else -> {
                throw IllegalArgumentException()
            }
        }
        binding.mealsGrid.adapter = adapter
        binding.title.text = title.makeTitle()
    }
        titleCategory = arguments?.getString(RECIPES_CATEGORY)
    }

    override fun addCallBacks() {
        binding.apply {
            title.text = titleCategory

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