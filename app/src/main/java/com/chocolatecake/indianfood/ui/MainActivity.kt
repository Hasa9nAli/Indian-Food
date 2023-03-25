package com.chocolatecake.indianfood.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.chocolatecake.indianfood.R
import com.chocolatecake.indianfood.databinding.ActivityMainBinding
import com.chocolatecake.indianfood.util.Constants.MAIN_ACTIVITY


class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val LOG_TAG: String = MAIN_ACTIVITY

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate

    private var onBoardingFragment: HomeFragment = HomeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        val transction = supportFragmentManager.beginTransaction()
        transction.add(R.id.fragment_container, onBoardingFragment)
        transction.commit()
    }

    override fun setUp() {
        //   installSplashScreen()
        // onBoardingFragment = RecipeDetailsFragment.newInstance(GetRandomMealIntractor(CsvDataSource(CsvParser(), this)).invoke())
    }

    override fun addCallbacks() {

    }


}