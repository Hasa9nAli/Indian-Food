package com.chocolatecake.indianfood.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chocolatecake.indianfood.ShowMoreAdapter
import com.chocolatecake.indianfood.dataSource.CsvDataSource
import com.chocolatecake.indianfood.dataSource.utils.CsvParser
import com.chocolatecake.indianfood.databinding.ShowMoreBinding
import com.chocolatecake.indianfood.interactor.GetHealthyMealsInteractor
import com.chocolatecake.indianfood.interactor.GetQuickRecipesInteractor

class ShowMoreFragment : BaseFragment<ShowMoreBinding>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> ShowMoreBinding =
        ShowMoreBinding::inflate


    override fun setUp() {
        lateinit var adapter: ShowMoreAdapter
        val csvData = CsvDataSource(CsvParser(), requireContext())
        val title: String = arguments?.getString(RECIPES_CATEGORY).toString().lowercase()
        if (title == GetHealthyMealsInteractor.HEALTHY)
            adapter = ShowMoreAdapter(GetHealthyMealsInteractor(csvData).invoke())
        else (title == GetQuickRecipesInteractor.QUICK_RECIPES)
        adapter = ShowMoreAdapter(GetQuickRecipesInteractor(csvData).invoke(8))

        binding.mealsGrid.adapter = adapter
        goBack()
    }

    private fun goBack() {
        val arrowBackButton = binding.arrowBack.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
    }

    override fun addCallBacks() {

    }

    companion object {
        const val RECIPES_CATEGORY = "RECIPE"

        fun newInstance(categoryType: String) =
            ShowMoreFragment().apply {
                arguments = Bundle().apply {
                    putString(RECIPES_CATEGORY, categoryType)
                }
            }
    }

}