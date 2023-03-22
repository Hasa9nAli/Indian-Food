package com.chocolatecake.indianfood

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    val onBaording = OnBoardingFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val trans = supportFragmentManager.beginTransaction()
        trans.add(R.id.container_fragment, onBaording)
        trans.commit()
    }
}