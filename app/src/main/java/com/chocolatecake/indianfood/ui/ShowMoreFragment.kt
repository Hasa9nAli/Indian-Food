package com.chocolatecake.indianfood.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chocolatecake.indianfood.databinding.FragmentShowMoreBinding

class ShowMoreFragment : BaseFragment<FragmentShowMoreBinding>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentShowMoreBinding =
        FragmentShowMoreBinding::inflate

    private var titleCategory: String? = null

    override fun setUp() {
        titleCategory = arguments?.getString(RECIPES_CATEGORY)
    }


    override fun addCallBacks() {
        binding.apply {
            title.text = titleCategory

            arrowBack.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }
    }

    companion object {
        private const val RECIPES_CATEGORY = "Recipes Category"

        fun newInstance(categoryType: String) =
            ShowMoreFragment().apply {
                arguments = Bundle().apply {
                    putString(RECIPES_CATEGORY, categoryType)
                }
            }
    }

}