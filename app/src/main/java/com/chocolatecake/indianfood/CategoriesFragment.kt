package com.chocolatecake.indianfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chocolatecake.indianfood.databinding.FragmentCategoriesBinding
import com.chocolatecake.indianfood.interactor.GetBreakfastRecipesInteractor
import com.chocolatecake.indianfood.interactor.GetDinnerRecipesInteractor
import com.chocolatecake.indianfood.interactor.GetLunchRecipesInteractor
import com.chocolatecake.indianfood.ui.BaseFragment
import com.chocolatecake.indianfood.ui.CategoriesRecipesFragment


class CategoriesFragment :BaseFragment<FragmentCategoriesBinding>(){

 private lateinit var  _binding:FragmentCategoriesBinding
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCategoriesBinding=
        FragmentCategoriesBinding::inflate


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding =FragmentCategoriesBinding.inflate(inflater, container, false)
        return _binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cardViewBreakfast.setOnClickListener {
            navigateToCategoriesRecipes(GetBreakfastRecipesInteractor.BREAKFAST)
        }
        binding.cardViewLunch.setOnClickListener {
            navigateToCategoriesRecipes(GetLunchRecipesInteractor.LUNCH)
        }
        binding.cardViewDinner.setOnClickListener {
            navigateToCategoriesRecipes(GetDinnerRecipesInteractor.DINNER)
        }
        }

        private fun navigateToCategoriesRecipes(mealType:String){
            val transaction = parentFragmentManager.beginTransaction()
            transaction.add(R.id.fragment_container, CategoriesRecipesFragment.newInstance(mealType))
            transaction.commit()
        }


    override fun setUp() {
    }

    override fun addCallBacks() {
    }

}

