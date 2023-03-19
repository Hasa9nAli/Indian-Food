package com.chocolatecake.indianfood.dataSource

import android.content.Context
import com.chocolatecake.indianfood.dataSource.utils.CsvParser
import com.chocolatecake.indianfood.interactor.IndianFoodDataSource
import com.chocolatecake.indianfood.model.Recipe
import java.io.InputStream

class CsvDataSource(
    private val parser: CsvParser,
    private val context: Context
) : IndianFoodDataSource {

    override fun getAllRecipesData(): List<Recipe> {
        return getCsvFile()
            .bufferedReader()
            .readText()
            .split(Regex(LINES_SEPARATOR))
            .drop(1)
            .map {
                parser.parseLine(it)
            }

//        context
//            .assets
//            .open(fileName)
//            .bufferedReader()
//            .use(BufferedReader::readText)

    }

    private fun getCsvFile(): InputStream {
        return context.assets.open(FILE_NAME)
    }

    companion object {
        private const val FILE_NAME = "indian_food_v7.csv"
        private const val LINES_SEPARATOR = ",#"
    }

}