package com.chocolatecake.indianfood.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chocolatecake.indianfood.databinding.FragmentInstructionsBinding

class InstructionsFragment : BaseFragment<FragmentInstructionsBinding>() {

    private lateinit var recipeInstructions: List<String>
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentInstructionsBinding =
        FragmentInstructionsBinding::inflate

    override fun addCallBacks() {

    }

    override fun setUp() {
        recipeInstructions = arguments?.getStringArrayList(INSTRUCTIONS)!!.toList()
        setIngredientsTextValue()
    }

    private fun setIngredientsTextValue() {
        val text = recipeInstructions.joinToString(separator = "\n")
        binding.textViewIngredientsShow.text = text
    }

    companion object {
        const val TAG = "IngredientsFragment"

        const val INSTRUCTIONS = "instructions"

        fun newInstance(instructions: ArrayList<String>) =
            InstructionsFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList(INSTRUCTIONS, instructions)
                }
            }
    }


}