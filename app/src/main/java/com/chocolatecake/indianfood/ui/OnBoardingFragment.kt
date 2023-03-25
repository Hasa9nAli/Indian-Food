package com.chocolatecake.indianfood.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chocolatecake.indianfood.databinding.FragmentOnBoardingBinding
import com.chocolatecake.indianfood.util.createOnBoardingDataList

class OnBoardingFragment : Fragment() {

    private lateinit var onBoardingPagerAdapter: OnBoardingPagerAdapter
    private lateinit var _binding: FragmentOnBoardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardingBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOnBoardingViewPager()
        setupNextButton()

    }

    private fun setupOnBoardingViewPager() {
        val onBoardingData = requireContext().createOnBoardingDataList()
        onBoardingPagerAdapter = OnBoardingPagerAdapter(onBoardingData)
        _binding.onBoardingVB.adapter = onBoardingPagerAdapter
    }

    private fun setupNextButton() {
        _binding.btnNext.setOnClickListener {
            if (getCurrentItemIndex(0) < 3) {
                _binding.onBoardingVB.setCurrentItem(getCurrentItemIndex(1), true)
            }
        }
    }

    private fun getCurrentItemIndex(index: Int) = _binding.onBoardingVB.currentItem + index
}