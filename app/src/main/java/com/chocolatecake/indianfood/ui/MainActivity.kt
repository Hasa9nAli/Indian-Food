package com.chocolatecake.indianfood.ui

import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.chocolatecake.indianfood.R
import com.chocolatecake.indianfood.databinding.ActivityMainBinding
import com.chocolatecake.indianfood.util.Constants.MAIN_ACTIVITY


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
            when (it.itemId) {
                R.id.action_home -> {
                    replaceFragment(homeFragment)
                    true
                }
                R.id.action_search -> {
                    replaceFragment(searchFragment)
                    true
                }
                R.id.action_categories -> {
                    replaceFragment(categoriesFragment)
                    true
                }
                R.id.action_ingredients -> {
                    replaceFragment(ingredientsSearchFragment)
                    true
                }
                R.id.action_about -> {
                    replaceFragment(aboutMealsFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun setOnBoardingFragment() {
        replaceFragment(onBoardingFragment)
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment_container, fragment)
        transaction.commit()
    }
}