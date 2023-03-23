package com.chocolatecake.indianfood.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chocolatecake.indianfood.databinding.FragmentIngredientsBinding
import com.chocolatecake.indianfood.model.Recipe
import com.chocolatecake.indianfood.util.Constants

class IngredientsFragment : BaseFragment<FragmentIngredientsBinding>() {

    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentIngredientsBinding =
        FragmentIngredientsBinding::inflate

    private lateinit var recipeIngredients: List<String>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipeIngredients = arguments?.getStringArrayList(Constants.INSTRUCTIONS_LIST)!!.toList()
        Log.i(TAG, recipeIngredients.toString())
    }

    override fun addCallBacks() {

    }

    companion object{
        const val TAG="IngredientsFragment"
    }

}