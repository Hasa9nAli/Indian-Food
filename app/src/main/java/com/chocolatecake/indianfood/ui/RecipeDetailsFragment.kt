package com.chocolatecake.indianfood.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.chocolatecake.indianfood.R
import com.chocolatecake.indianfood.databinding.FragmentRecipeDetailsBinding
import com.chocolatecake.indianfood.model.Recipe
import com.chocolatecake.indianfood.util.Constants
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener


class RecipeDetailsFragment : BaseFragment<FragmentRecipeDetailsBinding>() {

    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRecipeDetailsBinding =
        FragmentRecipeDetailsBinding::inflate

    private  var recipe : Recipe?=null
    private lateinit var ingredientsFragment: IngredientsFragment
    private lateinit var instructionsFragment: InstructionsFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        recipe = arguments?.getParcelable<Recipe>("recipe")
        ingredientsFragment = IngredientsFragment()
        instructionsFragment = InstructionsFragment()
        initInnerFragmentsBundles()
        setDefaultInnerFragment()
        updateRecipeDetails()
    }

    override fun setUp() {
        TODO("Not yet implemented")
    }

    private fun updateRecipeDetails() {
        updateTextViews()
        updateRecipeImageView()
    }

    private fun updateRecipeImageView() {
        Glide.with(this)
            .load("https://www.archanaskitchen.com/images/archanaskitchen/1-Author/b.yojana-gmail.com/Spicy_Thakkali_Rice_Tomato_Pulihora-1_edited.jpg")
            .into(binding.imageViewRecipe)
    }

    private fun updateTextViews() {
        val minuets = recipe?.totalTimeInMinutes.toString() + " " + requireActivity().getString(R.string.min)
        val numberOfIngredients =
            recipe?.ingredients?.size.toString() + " " + requireActivity().getString(R.string.ingredients)

        binding.apply {
            textViewRecipeName.text = recipe?.name
            textViewNativeCountry.text = recipe?.cuisine
            textViewTimeRequired.text = minuets
            textViewNumberOfIngredients.text = numberOfIngredients
            textViewEaseLevel.text = requireActivity().getString(R.string.easy)
        }
    }

    private fun setDefaultInnerFragment() {
        replaceInnerFragment(ingredientsFragment)
    }

    private fun initInnerFragmentsBundles() {
        val ingredientsFragmentBundle = Bundle().apply {
            putStringArrayList(Constants.INGREDIENTS_LIST, ArrayList(recipe?.ingredients))
        }

        ingredientsFragment.arguments = ingredientsFragmentBundle

        val instructionsFragmentBundle = Bundle().apply {
            putStringArrayList(Constants.INSTRUCTIONS_LIST, ArrayList(recipe?.instruction))
        }

        instructionsFragment.arguments = instructionsFragmentBundle
    }

    override fun addCallBacks() {
        binding.tabLayoutIngredientsInstructions.addOnTabSelectedListener(object :
            OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.apply {
                    when (tab) {
                        tabLayoutIngredientsInstructions.getTabAt(ingredientsTabIndex) -> replaceInnerFragment(
                            ingredientsFragment
                        )
                        tabLayoutIngredientsInstructions.getTabAt(instructionsTabIndex) -> replaceInnerFragment(
                            instructionsFragment
                        )
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        binding.imageButtonReturnBack.setOnClickListener {
            popBackStack(this)
        }
    }

    private fun popBackStack(fragment: Fragment) {
        val transaction = (activity as FragmentActivity).supportFragmentManager.beginTransaction()
        transaction.remove(fragment)
        transaction.commit()
    }

    private fun replaceInnerFragment(fragment: Fragment) {
        val transaction = (activity as FragmentActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    companion object {
        private const val ingredientsTabIndex = 0
        private const val instructionsTabIndex = 1
    }
}


