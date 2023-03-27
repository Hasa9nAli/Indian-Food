package com.chocolatecake.indianfood.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chocolatecake.indianfood.R
import com.chocolatecake.indianfood.dataSource.IndianFoodCsvDataSource
import com.chocolatecake.indianfood.dataSource.utils.CsvParser
import com.chocolatecake.indianfood.databinding.FragmentIngredientsSearchBinding
import com.chocolatecake.indianfood.interactor.FindRecipesContainsSpecifiedIngredientInteractor
import com.chocolatecake.indianfood.interactor.IndianFoodDataSource
import com.chocolatecake.indianfood.ui.base.BaseFragment

class IngredientsSearchFragment : BaseFragment<FragmentIngredientsSearchBinding>() {

    lateinit var dataSource: IndianFoodDataSource
    lateinit var searchRecipesByIngredient: FindRecipesContainsSpecifiedIngredientInteractor
    private lateinit var csvParser: CsvParser


    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentIngredientsSearchBinding
        get() = FragmentIngredientsSearchBinding::inflate

    override fun addCallBacks() {

        Log.i("TAG", "Recipe: ${searchRecipesByIngredient(listOf("oil","rice")).map { it.name }}")
    }
    private fun setupDatasource() {
        csvParser = CsvParser()
        dataSource = IndianFoodCsvDataSource(csvParser, requireContext())
        searchRecipesByIngredient = FindRecipesContainsSpecifiedIngredientInteractor(dataSource)    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ingredients_search, container, false)
    }

    override fun setUp() {

        setupDatasource()

    }
}