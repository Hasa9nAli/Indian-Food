package com.chocolatecake.indianfood.ui.about

import android.view.LayoutInflater
import android.view.ViewGroup
import com.chocolatecake.indianfood.databinding.FragmentAboutIndianFoodBinding
import com.chocolatecake.indianfood.ui.base.BaseFragment


class AboutIndianFoodFragment : BaseFragment<FragmentAboutIndianFoodBinding>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAboutIndianFoodBinding =
        FragmentAboutIndianFoodBinding::inflate

    override fun addCallBacks() {}

    override fun setUp() {}
}