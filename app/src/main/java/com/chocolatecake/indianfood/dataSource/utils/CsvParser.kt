package com.chocolatecake.indianfood.dataSource.utils

import com.chocolatecake.indianfood.model.Recipe


class CsvParser {

    fun parseLine(csvLine: String): Recipe {
        val tokenizedList: List<String> = csvLine.split(OBJECTS_SEPARATOR)
        return Recipe(
            name = tokenizedList[NAME],
            ingredients = constructIngredients(tokenizedList),
            totalTimeInMinutes = tokenizedList[TOTAL_TIME_IN_MINUTES].toInt(),
            cuisine = tokenizedList[CUISINE],
            instruction = constructInstructions(tokenizedList),
            sourceUrl = tokenizedList[SOURCE_URL],
            cleanedIngredients = constructCleanedIngredients(tokenizedList),
            imageUrl = tokenizedList[IMAGE_URL],
        )
    }

    private fun constructIngredients(tokenizedList: List<String>): List<String> {
        return tokenizedList[INGREDIENTS].splitBySemicolon()
    }

    private fun constructInstructions(tokenizedList: List<String>): List<String> {
        return tokenizedList[INSTRUCTIONS]
            .replace('"', ' ')
            .replace(";", ".")
            .removeEmptyLines()
            .splitByDots()
            .filter { it.isNotBlank() }
    }

    private fun constructCleanedIngredients(tokenizedList: List<String>): List<String> {
        return tokenizedList[CLEANED_INGREDIENTS].splitBySemicolon()
    }

    companion object {
        private const val NAME = 0
        private const val INGREDIENTS = 1
        private const val TOTAL_TIME_IN_MINUTES = 2
        private const val CUISINE = 3
        private const val INSTRUCTIONS = 4
        private const val SOURCE_URL = 5
        private const val CLEANED_INGREDIENTS = 6
        private const val IMAGE_URL = 7
        private const val OBJECTS_SEPARATOR = ","
    }

}