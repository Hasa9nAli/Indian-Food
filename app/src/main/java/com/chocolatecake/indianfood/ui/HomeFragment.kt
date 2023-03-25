package com.chocolatecake.indianfood.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chocolatecake.indianfood.dataSource.CsvDataSource
import com.chocolatecake.indianfood.dataSource.utils.CsvParser
import com.chocolatecake.indianfood.databinding.FragmentHomeBinding
import com.chocolatecake.indianfood.interactor.GetHealthyMealsInteractor
import com.chocolatecake.indianfood.interactor.GetQuickRecipesInteractor
import com.chocolatecake.indianfood.interactor.GetRandomMealIntractor
import com.chocolatecake.indianfood.interactor.IndianFoodDataSource
import com.chocolatecake.indianfood.model.HomeItem
import com.chocolatecake.indianfood.model.toHomeItem
import com.chocolatecake.indianfood.util.HomeItemType

class HomeFragment : BaseFragment<FragmentHomeBinding>(), OnClickShowMore, OnClickRecipe,
    OnClickRandomRecipe {

    private lateinit var dataSource: IndianFoodDataSource
    private lateinit var getQuickRecipes: GetQuickRecipesInteractor
    private lateinit var getRandomRecipes: GetRandomMealIntractor
    private lateinit var getHealthyRecipes: GetHealthyMealsInteractor
    private lateinit var itemsList: MutableList<HomeItem<Any>>
    private lateinit var csvParser: CsvParser


    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun setUp() {

    }

    override fun addCallBacks() {
        setupDatasource()
    }

    private fun setupDatasource() {
        csvParser = CsvParser()
        dataSource = CsvDataSource(csvParser, requireContext())
        getQuickRecipes = GetQuickRecipesInteractor(dataSource)
        getRandomRecipes = GetRandomMealIntractor(dataSource)
        getHealthyRecipes = GetHealthyMealsInteractor(dataSource)


        val itemsList: MutableList<HomeItem<Any>> = mutableListOf()

        itemsList.add(HomeItem(getRandomRecipes.invoke(), HomeItemType.RANDOM_RECIPES))
        itemsList.add(HomeItem("Quick Recipes", HomeItemType.TEXT))
        itemsList.addAll(getQuickRecipes.invoke(10).map { it.toHomeItem() })
        Log.i("HomeFragmentt", "list: ${getQuickRecipes.invoke(10).map { it.toHomeItem() }}")

        //   itemsList.addAll(getHealthyRecipes().map { it.toHomeItem() })
        binding.recipiesRecyclerView.adapter = HomeAdapter(itemsList, this, this, this)
//        binding.recipiesRecyclerView.adapter = RecipeAdapter(getQuickRecipes.invoke(10),  this)

    }

    override fun onClickShowMore() {
        TODO("Not yet implemented")
    }

    override fun onClickRandomRecipe() {
        TODO("Not yet implemented")
    }

    override fun onClickRecipe() {
        TODO("Not yet implemented")
    }


}
