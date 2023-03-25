package com.chocolatecake.indianfood.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.chocolatecake.indianfood.databinding.FragmentOnBoardingBinding
import com.chocolatecake.indianfood.util.createOnBoardingDataList

class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding>() {

    private lateinit var onBoardingPagerAdapter: OnBoardingPagerAdapter
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentOnBoardingBinding
        get() = FragmentOnBoardingBinding::inflate

    override fun setUp() {
        setupOnBoardingViewPager()
        setupNextButton()
    }

    override fun addCallBacks() {
    }

    private fun setupOnBoardingViewPager() {
        val onBoardingData = requireContext().createOnBoardingDataList()
        onBoardingPagerAdapter = OnBoardingPagerAdapter(onBoardingData)
        binding.onBoardingVB.adapter = onBoardingPagerAdapter
    }

    private fun setupNextButton() {
        binding.btnNext.setOnClickListener {
            if (getCurrentItemIndex(0) < 3) {
                binding.onBoardingVB.setCurrentItem(getCurrentItemIndex(1), true)
            }
        }
    }

    private fun getCurrentItemIndex(index: Int) = binding.onBoardingVB.currentItem + index
}