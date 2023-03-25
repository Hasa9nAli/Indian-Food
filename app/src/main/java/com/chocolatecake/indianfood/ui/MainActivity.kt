package com.chocolatecake.indianfood.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.chocolatecake.indianfood.R
import com.chocolatecake.indianfood.dataSource.CsvDataSource
import com.chocolatecake.indianfood.dataSource.utils.CsvParser
import com.chocolatecake.indianfood.databinding.ActivityMainBinding
import com.chocolatecake.indianfood.interactor.GetRandomMealIntractor
import com.chocolatecake.indianfood.util.Constants.MAIN_ACTIVITY


class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val LOG_TAG: String = MAIN_ACTIVITY

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate
    // private var onBoardingFragment: IngredientsSearchFragment? = null

    override fun setUp() {
        installSplashScreen()
//        onBoardingFragment = IngredientsSearchFragment.newInstance(
//            GetRandomMealIntractor(
//                CsvDataSource(
//                    CsvParser(), this
//                )
//            ).invoke()
//        )
    }

    override fun addCallbacks() {
        // setUpSubFragment()
    }


//    private fun setUpSubFragment() {
//        val transction = supportFragmentManager.beginTransaction()
//        transction.add(binding.fragmentContainer.id, onBoardingFragment!!)
//        transction.commit()
//    }
}