package com.chocolatecake.indianfood.ui

import android.view.LayoutInflater
import com.chocolatecake.indianfood.R
import com.chocolatecake.indianfood.databinding.ActivityMainBinding
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
    private lateinit var aboutMealsFragment: AboutMealsFragment

    override fun setUp() {
        onBoardingFragment = OnBoardingFragment()
        homeFragment = HomeFragment()
        searchFragment = IngredientsSearchFragment()
        categoriesFragment = CategoriesFragment()
        ingredientsSearchFragment = IngredientsSearchFragment()
        aboutMealsFragment = AboutMealsFragment()
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
                aboutMealsFragment
            }

            else -> {
                null
            }
        }

    private fun setOnBoardingFragment() {
        navigateExclusive(onBoardingFragment)
    }
}