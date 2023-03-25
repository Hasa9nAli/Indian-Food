package com.chocolatecake.indianfood.ui
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chocolatecake.indianfood.databinding.FragmentAboutMealsBinding
import com.chocolatecake.indianfood.ui.base.BaseFragment


class AboutMealsFragment : BaseFragment<FragmentAboutMealsBinding>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAboutMealsBinding =
        FragmentAboutMealsBinding::inflate

    override fun addCallBacks() {}

    override fun setUp() {}
}