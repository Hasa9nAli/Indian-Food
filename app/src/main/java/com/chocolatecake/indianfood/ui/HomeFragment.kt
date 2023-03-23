package com.chocolatecake.indianfood.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chocolatecake.indianfood.R
import com.chocolatecake.indianfood.dataSource.CsvDataSource
import com.chocolatecake.indianfood.dataSource.utils.CsvParser
import com.chocolatecake.indianfood.databinding.FragmentHomeBinding
import com.chocolatecake.indianfood.interactor.GetQuickRecipesInteractor
import com.chocolatecake.indianfood.interactor.GetRandomMealIntractor
import com.chocolatecake.indianfood.interactor.IndianFoodDataSource

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private lateinit var dataSource: IndianFoodDataSource
    private lateinit var getQuickRecipesInteractor: GetQuickRecipesInteractor
    private lateinit var getRandomMealIntractor: GetRandomMealIntractor
    private lateinit var csvParser: CsvParser


    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDatasource()
        getRandomRecipe()
        Log.i("TAG", "Quick Recipes: ${getQuickRecipesInteractor(3).map { it.name }}")
    }

    override fun addCallBacks() {
    }

    private fun setupDatasource() {
        csvParser = CsvParser()
        dataSource = CsvDataSource(csvParser, requireContext())
        getQuickRecipesInteractor = GetQuickRecipesInteractor(dataSource)
        getRandomMealIntractor = GetRandomMealIntractor(dataSource)
    }

    private fun getRandomRecipe() {
        val randomRecipe = getRandomMealIntractor.invoke()

        Glide.with(this).load(randomRecipe.imageUrl).into(binding.dishOfTheDayImage)

        binding.RecipeName.text = randomRecipe.name
        binding.RecipeCookingTime.text = "${randomRecipe.totalTimeInMinutes} min"
        binding.RecipeCuisine.text = randomRecipe.cuisine
    }

}
