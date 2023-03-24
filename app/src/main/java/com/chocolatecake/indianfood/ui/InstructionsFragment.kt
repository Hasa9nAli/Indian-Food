package com.chocolatecake.indianfood.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.chocolatecake.indianfood.databinding.FragmentInstructionsBinding
import com.chocolatecake.indianfood.util.Constants

class InstructionsFragment : BaseFragment<FragmentInstructionsBinding>() {

    private lateinit var recipeInstructions: List<String>
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentInstructionsBinding =
        FragmentInstructionsBinding::inflate

    override fun addCallBacks() {

    }

    override fun setUp() {
        recipeInstructions = arguments?.getStringArrayList(Constants.INSTRUCTIONS_LIST)!!.toList()
        setIngredientsTextValue()
    }

    private fun setIngredientsTextValue() {
        val text = recipeInstructions.joinToString(separator = "\n")
        binding.textViewIngredientsShow.text = text
    }


}