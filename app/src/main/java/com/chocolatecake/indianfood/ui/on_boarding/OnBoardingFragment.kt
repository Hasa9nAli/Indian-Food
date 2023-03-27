package com.chocolatecake.indianfood.ui.on_boarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chocolatecake.indianfood.R
import com.chocolatecake.indianfood.dataSource.IndianFoodCsvDataSource
import com.chocolatecake.indianfood.dataSource.utils.CsvParser
import com.chocolatecake.indianfood.databinding.FragmentOnBoardingBinding
import com.chocolatecake.indianfood.interactor.GetOnBoardingDataInteractor
import com.chocolatecake.indianfood.ui.base.BaseFragment
import com.chocolatecake.indianfood.ui.home.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding>() {

    private lateinit var onBoardingPagerAdapter: OnBoardingPagerAdapter
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentOnBoardingBinding
        get() = FragmentOnBoardingBinding::inflate

    override fun setUp() {
        setupOnBoardingViewPagerAdapter()
        setupNextButton()
    }

    override fun addCallBacks() {
    }

    private fun setupOnBoardingViewPagerAdapter() {
        val onBoardingData =
            GetOnBoardingDataInteractor(
                IndianFoodCsvDataSource(
                    CsvParser(),
                    requireContext()
                )
            ).invoke()
        onBoardingPagerAdapter = OnBoardingPagerAdapter(onBoardingData)
        binding.viewPagerOnboarding.adapter = onBoardingPagerAdapter
    }

    private fun setupNextButton() {
        binding.buttonNext.setOnClickListener {
            val currentItemIndex = getCurrentItemIndex()
            val lastItemIndex = getLastItemIndex()
            if (currentItemIndex < lastItemIndex) {
                binding.viewPagerOnboarding.setCurrentItem(currentItemIndex + 1, true)
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
        val bottomNavigationBar =
            requireActivity().findViewById<BottomNavigationView>(R.id.main_bottom_navigation)
        bottomNavigationBar.visibility = View.VISIBLE
    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .commit()
    }

    private fun getLastItemIndex() = binding.viewPagerOnboarding.adapter?.count?.minus(1) ?: -1
    private fun getCurrentItemIndex() = binding.viewPagerOnboarding.currentItem
}