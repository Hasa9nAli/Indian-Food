package com.chocolatecake.indianfood.interactor

import com.chocolatecake.indianfood.model.OnBoardingItem

class GetOnBoardingDataInteractor(
    private val dataSource: IndianFoodDataSource,
) {
    operator fun invoke(): List<OnBoardingItem> = dataSource.getOnBoardingData()
}