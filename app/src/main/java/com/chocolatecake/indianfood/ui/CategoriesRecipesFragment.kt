package com.chocolatecake.indianfood.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chocolatecake.indianfood.R
import com.chocolatecake.indianfood.dataSource.CsvDataSource
import com.chocolatecake.indianfood.dataSource.utils.CsvParser
import com.chocolatecake.indianfood.databinding.FragmentCategoriesRecipesBinding
import com.chocolatecake.indianfood.interactor.GetBreakfastRecipesInteractor
import com.chocolatecake.indianfood.interactor.GetDinnerRecipesInteractor
import com.chocolatecake.indianfood.interactor.GetLunchRecipesInteractor
import com.chocolatecake.indianfood.model.Recipe


class CategoriesRecipesFragment : BaseFragment<FragmentCategoriesRecipesBinding>() {


    private lateinit var categoryRecipesAdapter: CategoryRecipesAdapter
    private lateinit var recipes: List<Recipe>
    private var param1: String? = null
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCategoriesRecipesBinding
        get() = FragmentCategoriesRecipesBinding::inflate

    override fun setUp() {

        arguments?.let {
            param1 = it.getString(MEAL_TYPE)
        }

        recipes = when (param1) {
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
        setUpRecyclerView()
        navigateBackToCategoryFragment()
        binding.textMeals.text = param1
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

    private fun setUpRecyclerView() {
        categoryRecipesAdapter = CategoryRecipesAdapter(requireContext(), recipes)
        binding.recyclerRecipe.adapter = categoryRecipesAdapter
    }

    private fun navigateBackToCategoryFragment() {
        binding.imgBack.setOnClickListener {
            onBackButtonClicked()
        }

    }

    private fun onBackButtonClicked() {
        val categoriesFragment = CategoriesFragment()
        val transaction = parentFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, categoriesFragment)
            commit()
        }
    }
}