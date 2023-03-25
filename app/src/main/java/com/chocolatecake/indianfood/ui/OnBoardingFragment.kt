package com.chocolatecake.indianfood.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.chocolatecake.indianfood.R
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
        binding.onBoardingViewPager.adapter = onBoardingPagerAdapter
    }

    private fun setupNextButton() {
        binding.buttonNext.setOnClickListener {
            val currentItemIndex = getCurrentItemIndex()
            val lastItemIndex = getLastItemIndex()
            if (currentItemIndex < lastItemIndex) {
                binding.onBoardingViewPager.setCurrentItem(currentItemIndex + 1, true)
            } else {
                replaceFragment(HomeFragment())
            }
        }

        binding.buttonSkip.setOnClickListener {
            replaceFragment(HomeFragment())
        }

    }

    override fun onDetach() {
        super.onDetach()
        val binding = (activity as MainActivity?)!!.binding
        binding.mainBottomNavigation.visibility = View.VISIBLE
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = (activity as FragmentActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_container, fragment)
        transaction.commit()
    }

    private fun getLastItemIndex() = binding.onBoardingViewPager.adapter?.count?.minus(1) ?: -1
    private fun getCurrentItemIndex() = binding.onBoardingViewPager.currentItem
}