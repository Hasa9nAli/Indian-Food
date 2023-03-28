package com.chocolatecake.indianfood.ui

import android.view.LayoutInflater
import com.chocolatecake.indianfood.R
import com.chocolatecake.indianfood.databinding.ActivityMainBinding
import com.chocolatecake.indianfood.ui.about.AboutIndianFoodFragment
import com.chocolatecake.indianfood.ui.base.BaseActivity
import com.chocolatecake.indianfood.ui.categories.CategoriesFragment
import com.chocolatecake.indianfood.ui.home.HomeFragment
import com.chocolatecake.indianfood.ui.on_boarding.OnBoardingFragment
import com.chocolatecake.indianfood.util.Constants.MAIN_ACTIVITY
import com.chocolatecake.indianfood.util.navigateExclusive


class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val LOG_TAG: String = MAIN_ACTIVITY

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate

    private lateinit var onBoardingFragment: OnBoardingFragment
    private lateinit var homeFragment: HomeFragment
    private lateinit var searchFragment: IngredientsSearchFragment
    private lateinit var categoriesFragment: CategoriesFragment
    private lateinit var ingredientsSearchFragment: IngredientsSearchFragment
    private lateinit var aboutIndianFoodFragment: AboutIndianFoodFragment

    override fun setUp() {
        onBoardingFragment = OnBoardingFragment()
        homeFragment = HomeFragment()
        searchFragment = IngredientsSearchFragment()
        categoriesFragment = CategoriesFragment()
        ingredientsSearchFragment = IngredientsSearchFragment()
        aboutIndianFoodFragment = AboutIndianFoodFragment()
        setOnBoardingFragment()
    }

    override fun addCallbacks() {
        binding.mainBottomNavigation.setOnItemSelectedListener {
            val destinationFragment = getDestinationFragment(it.itemId)
            destinationFragment?.let {
                navigateExclusive(destinationFragment)
                return@setOnItemSelectedListener true
            }
            return@setOnItemSelectedListener false
        }
    }

    private fun getDestinationFragment(bottomNavigationBarItemId: Int) =
        when (bottomNavigationBarItemId) {
            R.id.action_home -> {
                homeFragment
            }

            R.id.action_search -> {
                searchFragment
            }

            R.id.action_categories -> {
                categoriesFragment
            }

            R.id.action_ingredients -> {
                ingredientsSearchFragment
            }

            R.id.action_about -> {
                aboutIndianFoodFragment
            }

            else -> {
                null
            }
        }

    private fun setOnBoardingFragment() {
        navigateExclusive(homeFragment)
    }
}