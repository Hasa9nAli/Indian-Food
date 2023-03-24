package com.chocolatecake.indianfood.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chocolatecake.indianfood.R
import com.chocolatecake.indianfood.dataSource.CsvDataSource
import com.chocolatecake.indianfood.dataSource.utils.CsvParser
import com.chocolatecake.indianfood.databinding.FragmentIngredientsSearchBinding
import com.chocolatecake.indianfood.interactor.FindRecipesByAnyIngredientsInteractor
import com.chocolatecake.indianfood.interactor.FindRecipesContainsSpecifiedIngredientInteractor
import com.chocolatecake.indianfood.interactor.GetRandomMealIntractor
import com.chocolatecake.indianfood.interactor.IndianFoodDataSource

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class IngredientsSearchFragment : BaseFragment<FragmentIngredientsSearchBinding>() {
    private var param1: String? = null
    private var param2: String? = null

    lateinit var dataSource: IndianFoodDataSource
    lateinit var searchRecipesByIngredient: FindRecipesContainsSpecifiedIngredientInteractor
    private lateinit var csvParser: CsvParser


    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentIngredientsSearchBinding
        get() = FragmentIngredientsSearchBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun addCallBacks() {

        Log.i("TAG", "Recipe: ${searchRecipesByIngredient(listOf("oil","rice")).map { it.name }}")
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDatasource()
    }

    private fun setupDatasource() {
        csvParser = CsvParser()
        dataSource = CsvDataSource(csvParser, requireContext())
        searchRecipesByIngredient = FindRecipesContainsSpecifiedIngredientInteractor(dataSource)    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ingredients_search, container, false)
    }

    override fun setUp() {
    }
}