package com.chocolatecake.indianfood.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chocolatecake.indianfood.dataSource.CsvDataSource
import com.chocolatecake.indianfood.dataSource.utils.CsvParser
import com.chocolatecake.indianfood.databinding.FragmentCategoryRecipesBinding
import com.chocolatecake.indianfood.interactor.GetBreakfastRecipesInteractor
import com.chocolatecake.indianfood.interactor.GetDinnerRecipesInteractor
import com.chocolatecake.indianfood.interactor.GetLunchRecipesInteractor


class CategoryRecipesFragment : BaseFragment<FragmentCategoryRecipesBinding>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCategoryRecipesBinding
        get() = FragmentCategoryRecipesBinding::inflate

    override fun setUp() {
        binding.textViewAppBarTitle.text = getMealType()
        setUpRecipesRecyclerView(
            CategoryRecipesAdapter(
                requireContext(),
                getMealRecipes(getMealType())
            )
        )
    }

    private fun getMealRecipes(mealType: String) = when (mealType) {
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

    private fun getMealType() = requireArguments().getString(MEAL_TYPE)!!

    override fun addCallBacks() {
        navigateBackToCategoryFragment()
    }

    private fun setUpRecipesRecyclerView(
        categoryRecipesAdapter: CategoryRecipesAdapter,
    ) {
        binding.recyclerRecipe.adapter = categoryRecipesAdapter
    }

    private fun navigateBackToCategoryFragment() {
        binding.imgBack.setOnClickListener {
            onBackButtonClicked()
        }
    }

    private fun onBackButtonClicked() {
        parentFragmentManager.popBackStack()
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