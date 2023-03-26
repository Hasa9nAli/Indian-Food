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

class ShowMoreFragment : BaseFragment<ShowMoreBinding>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> ShowMoreBinding =
        ShowMoreBinding::inflate


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
        goBack()
    }

    private fun goBack() {
        val arrowBackButton = binding.arrowBack.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
    }

    override fun addCallBacks() {

    }

    private fun String.makeTitle() =
        this.split(" ").joinToString(" ") { it.replaceFirstChar(Char::titlecase) }

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