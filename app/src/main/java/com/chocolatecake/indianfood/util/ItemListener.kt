package com.chocolatecake.indianfood.util

import com.chocolatecake.indianfood.model.Recipe

interface ItemListener {
    fun onClickItem(recipe: Recipe)
}