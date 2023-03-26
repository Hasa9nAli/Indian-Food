package com.chocolatecake.indianfood.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.chocolatecake.indianfood.dataSource.CsvDataSource
import com.chocolatecake.indianfood.dataSource.utils.CsvParser
import com.chocolatecake.indianfood.databinding.FragmentHomeBinding
import com.chocolatecake.indianfood.interactor.GetBreakfastRecipesInteractor
import com.chocolatecake.indianfood.interactor.GetHealthyRecipesInteractor
import com.chocolatecake.indianfood.interactor.GetQuickRecipesInteractor
import com.chocolatecake.indianfood.interactor.GetRandomMealIntractor
import com.chocolatecake.indianfood.interactor.IndianFoodDataSource
import com.chocolatecake.indianfood.model.HomeItem
import com.chocolatecake.indianfood.model.Recipe
import com.chocolatecake.indianfood.util.HomeItemType
import com.chocolatecake.indianfood.util.navigateTo

class HomeFragment : BaseFragment<FragmentHomeBinding>(), OnClickShowMore, OnClickRecipe,
    OnClickRandomRecipe {

    private lateinit var dataSource: IndianFoodDataSource
    private lateinit var getQuickRecipes: GetQuickRecipesInteractor
    private lateinit var getRandomRecipes: GetRandomMealIntractor
    private lateinit var getHealthyRecipes: GetHealthyRecipesInteractor
    private lateinit var getBreakfastRecipes: GetBreakfastRecipesInteractor
    private lateinit var csvParser: CsvParser


    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun setUp() {

    }

    override fun addCallBacks() {
        setupDatasource()
        setupHomeAdapter()
    }

    private fun setupDatasource() {
        csvParser = CsvParser()
        dataSource = CsvDataSource(csvParser, requireContext())
        getQuickRecipes = GetQuickRecipesInteractor(dataSource)
        getRandomRecipes = GetRandomMealIntractor(dataSource)
        getHealthyRecipes = GetHealthyRecipesInteractor(dataSource)
        getBreakfastRecipes = GetBreakfastRecipesInteractor(dataSource)

    }

    private fun setupHomeAdapter() {
        val itemsList: MutableList<HomeItem<Any>> = mutableListOf()

        itemsList.add(HomeItem(getRandomRecipes.invoke(), HomeItemType.TYPE_RANDOM_RECIPES))

        itemsList.add(HomeItem(QUICK_RECIPES, HomeItemType.TYPE_TEXT))
        itemsList.add(HomeItem(getQuickRecipes.invoke(10), HomeItemType.TYPE_RECIPE))

        itemsList.add(HomeItem(HEALTHY_MEALS, HomeItemType.TYPE_TEXT))
        itemsList.add(HomeItem(getHealthyRecipes.invoke(10), HomeItemType.TYPE_RECIPE))

//        itemsList.add(HomeItem(BREAKFAST, HomeItemType.TYPE_TEXT))
//        itemsList.add(HomeItem(getBreakfastRecipes.invoke(), HomeItemType.TYPE_RECIPE))

        binding.recipiesRecyclerView.adapter = HomeAdapter(itemsList, this, this, this)
    }

    override fun onClickShowMore(categoryType: String) {
        val showMoreFragment = ShowMoreFragment.newInstance(categoryType)
        requireActivity().navigateTo(showMoreFragment)
    }

    override fun onClickRandomRecipe(recipe: Recipe) {
        val detailsFragment = RecipeDetailsFragment.newInstance(recipe)
        requireActivity().navigateTo(detailsFragment)
    }

    override fun onClickRecipe(recipe: Recipe) {
        val detailsFragment = RecipeDetailsFragment.newInstance(recipe)
        requireActivity().navigateTo(detailsFragment)
    }

    companion object {
        const val HEALTHY_MEALS = "Healthy meals"
        const val QUICK_RECIPES = "Quick recipes"
        const val BREAKFAST = "Breakfast"
    }


}
