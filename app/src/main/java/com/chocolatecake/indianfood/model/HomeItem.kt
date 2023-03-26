package com.chocolatecake.indianfood.model

import com.chocolatecake.indianfood.util.HomeItemType

data class HomeItem<T>(val item: T, val type: HomeItemType)
