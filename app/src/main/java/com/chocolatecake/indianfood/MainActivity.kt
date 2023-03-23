package com.chocolatecake.indianfood

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chocolatecake.indianfood.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val onBoardingFragment = OnBoardingFragment()
    private lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        setUpSubFragment()

    }

    private fun setUpSubFragment() {

        val transction = supportFragmentManager.beginTransaction()
        transction.add(_binding.fragmentContainer.id, onBoardingFragment)
        transction.commit()

    }
}