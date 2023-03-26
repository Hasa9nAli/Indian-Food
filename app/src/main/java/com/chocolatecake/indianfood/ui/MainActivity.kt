package com.chocolatecake.indianfood.ui

import android.view.LayoutInflater
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.chocolatecake.indianfood.databinding.ActivityMainBinding
import com.chocolatecake.indianfood.interactor.GetQuickRecipesInteractor
import com.chocolatecake.indianfood.util.Constants.MAIN_ACTIVITY


class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val LOG_TAG: String = MAIN_ACTIVITY

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate
    private var onBoardingFragment: ShowMoreFragment? = null

    override fun setUp() {
        installSplashScreen()
        onBoardingFragment = ShowMoreFragment.newInstance(GetQuickRecipesInteractor.QUICK_RECIPES)
    }

    override fun addCallbacks() {
        setUpSubFragment()
    }


    private fun setUpSubFragment() {
        val transction = supportFragmentManager.beginTransaction()
        transction.add(binding.fragmentContainer.id, onBoardingFragment!!)
        transction.commit()
    }
}