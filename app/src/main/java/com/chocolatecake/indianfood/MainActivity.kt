package com.chocolatecake.indianfood

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
     val myFirstFragment=CategoriesFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initSubView()
    }

    private fun initSubView() {
       val transaction= supportFragmentManager.beginTransaction()
        transaction.add(R.id.fragment_categories,myFirstFragment)
        transaction
    }
}