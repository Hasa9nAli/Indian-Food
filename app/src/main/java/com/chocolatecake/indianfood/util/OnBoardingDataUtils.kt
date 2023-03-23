package com.chocolatecake.indianfood.util

import android.content.Context
import com.chocolatecake.indianfood.R
import com.chocolatecake.indianfood.model.OnBoardingItem

fun Context.createOnBoardingDataList(): MutableList<OnBoardingItem> {
    val onBoardingData: MutableList<OnBoardingItem> = ArrayList()
    onBoardingData.add(
        OnBoardingItem(
            getString(R.string.onBoarding_title_1),
            getString(R.string.onBoarding_desc_1),
            R.drawable.onboarding_slide_1
        )
    )
    onBoardingData.add(
        OnBoardingItem(
            getString(R.string.onBoarding_title_2),
            getString(R.string.onBoarding_desc_2),
            R.drawable.onboarding_slide_2
        )
    )
    onBoardingData.add(
        OnBoardingItem(
            getString(R.string.onBoarding_title_3),
            getString(R.string.onBoarding_desc_3),
            R.drawable.onboarding_slide_3
        )
    )

    return onBoardingData
}