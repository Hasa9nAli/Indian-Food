package com.chocolatecake.indianfood.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chocolatecake.indianfood.databinding.ShowMoreBinding

class ShowMoreFragment : BaseFragment<ShowMoreBinding>() {
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

    companion object {
        const val RECIPES_CATEGORY = "RECIPE"

        fun newInstance(categoryType: String) =
            RecipeDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(RECIPES_CATEGORY, categoryType)
                }
            }
    }

}