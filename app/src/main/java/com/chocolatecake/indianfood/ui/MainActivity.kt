package com.chocolatecake.indianfood.ui

import android.view.LayoutInflater
import com.chocolatecake.indianfood.databinding.ActivityMainBinding
import com.chocolatecake.indianfood.util.Constants.MAIN_ACTIVITY


class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val LOG_TAG: String = MAIN_ACTIVITY

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate
    private var onBoardingFragment: OnBoardingFragment? = null

    override fun setUp() {
        onBoardingFragment = OnBoardingFragment()
    }

    override fun addCallbacks() {
        setUpSubFragment()
    }


    private fun setUpSubFragment() {
        val transction = supportFragmentManager.beginTransaction()
        transction.add(binding.mainFragmentContainer.id, onBoardingFragment!!)
        transction.commit()
    }
}