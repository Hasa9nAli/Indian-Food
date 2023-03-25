package com.chocolatecake.indianfood

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import com.chocolatecake.indianfood.databinding.ShowMoreBinding
import com.chocolatecake.indianfood.model.Recipe
import com.chocolatecake.indianfood.ui.BaseFragment

class ShowMoreFragment() : BaseFragment<ShowMoreBinding>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> ShowMoreBinding =
        ShowMoreBinding::inflate


    override fun setUp() {
        goBack()
    }

    private fun goBack() {

        val arrowBackButton = binding.arrowBack.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
    }

    override fun addCallBacks() {

    }



}