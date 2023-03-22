package com.chocolatecake.indianfood.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chocolatecake.indianfood.dataSource.CsvDataSource
import com.chocolatecake.indianfood.dataSource.utils.CsvParser
import com.chocolatecake.indianfood.databinding.FragmentHomeBinding
import com.chocolatecake.indianfood.interactor.GetQuickRecipesInteractor
import com.chocolatecake.indianfood.interactor.IndianFoodDataSource

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private lateinit var dataSource: IndianFoodDataSource
    private lateinit var getQuickRecipesInteractor: GetQuickRecipesInteractor
    private lateinit var csvParser: CsvParser


    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDatasource()
    }

    override fun addCallBacks() {
        Log.i("TAG", "Quick Recipes: ${getQuickRecipesInteractor(10).map { it.name }}")
    }

    private fun setupDatasource() {
        csvParser = CsvParser()
        dataSource = CsvDataSource(csvParser, requireContext())
        getQuickRecipesInteractor = GetQuickRecipesInteractor(dataSource)

    }
}

