package com.chocolatecake.indianfood.dataSource

import android.content.Context
import com.chocolatecake.indianfood.R
import com.chocolatecake.indianfood.dataSource.utils.CsvParser
import com.chocolatecake.indianfood.interactor.IndianFoodDataSource
import com.chocolatecake.indianfood.model.OnBoardingItem
import com.chocolatecake.indianfood.model.Recipe
import java.io.InputStream

class IndianFoodCsvDataSource(
    private val context: Context
) : IndianFoodDataSource {

    private val parser: CsvParser = CsvParser()

    override fun getAllRecipesData(): List<Recipe> {
        return getCsvFile()
            .bufferedReader()
            .readText()
            .split(Regex(LINES_SEPARATOR))
            .drop(1)
            .map {
                parser.parseLine(it)
            }
    }

    override fun getOnBoardingData(): List<OnBoardingItem> {
        return listOf(
            OnBoardingItem(
                context.getString(R.string.onBoarding_title_1),
                context.getString(R.string.onBoarding_desc_1),
                R.drawable.chef
            ),
            OnBoardingItem(
                context.getString(R.string.onBoarding_title_2),
                context.getString(R.string.onBoarding_desc_2),
                R.drawable.shawarma
            ),
            OnBoardingItem(
                context.getString(R.string.onBoarding_title_3),
                context.getString(R.string.onBoarding_desc_3),
                R.drawable.recipe
            )
        )
    }

    private fun getCsvFile(): InputStream {
        return context.assets.open(FILE_NAME)
    }

    companion object {
        private const val FILE_NAME = "indian_food_v7.csv"
        private const val LINES_SEPARATOR = ",#"
    }

}