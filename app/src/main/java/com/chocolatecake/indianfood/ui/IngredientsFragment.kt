package com.chocolatecake.indianfood.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chocolatecake.indianfood.databinding.FragmentIngredientsBinding
import com.chocolatecake.indianfood.ui.base.BaseFragment

class IngredientsFragment : BaseFragment<FragmentIngredientsBinding>() {

    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentIngredientsBinding =
        FragmentIngredientsBinding::inflate

    private lateinit var recipeIngredients: List<String>

    override fun setUp() {
        recipeIngredients =
            arguments?.getStringArrayList(INGREDIENTS)!!.toList()
        Log.i(TAG, recipeIngredients.toString())
    }

    override fun addCallBacks() {

    }
    
    companion object {
        const val TAG = "IngredientsFragment"
        const val INGREDIENTS = "ingredients"

        fun newInstance(ingredients: ArrayList<String>) =
            IngredientsFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList(INGREDIENTS, ingredients)
                }
            }
    }
}