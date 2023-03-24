package com.chocolatecake.indianfood.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chocolatecake.indianfood.databinding.FragmentInstructionsBinding
import com.chocolatecake.indianfood.util.Constants

class InstructionsFragment : BaseFragment<FragmentInstructionsBinding>() {

    private lateinit var recipeInstructions: List<String>
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentInstructionsBinding =
        FragmentInstructionsBinding::inflate

    override fun addCallBacks() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipeInstructions = arguments?.getStringArrayList(Constants.INSTRUCTIONS_LIST)!!.toList()
        setIngredientsTextValue()
    }

    override fun setUp() {
        TODO("Not yet implemented")
    }

    private fun setIngredientsTextValue() {
        val text = recipeInstructions.joinToString(separator = "\n")
        binding.textViewIngredientsShow.text = text
    }


}