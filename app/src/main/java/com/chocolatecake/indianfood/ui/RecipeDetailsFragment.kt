package com.chocolatecake.indianfood.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.chocolatecake.indianfood.R
import com.chocolatecake.indianfood.databinding.FragmentRecipeDetailsBinding
import com.chocolatecake.indianfood.model.Recipe
import com.chocolatecake.indianfood.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener


class RecipeDetailsFragment : BaseFragment<FragmentRecipeDetailsBinding>(), OnTabSelectedListener {

    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRecipeDetailsBinding =
        FragmentRecipeDetailsBinding::inflate

    private var recipe: Recipe? = null
    private lateinit var ingredientsFragment: IngredientsFragment
    private lateinit var instructionsFragment: InstructionsFragment

    override fun setUp() {
        initRecipeObjectFromParameter()
        initIngredientsAndInstructionsFragmentsObjects(
            ArrayList(recipe!!.ingredients),
            ArrayList(recipe!!.instruction)
        )
        setDefaultInnerFragment()
        updateRecipeDetailsViews()
    }

    private fun initIngredientsAndInstructionsFragmentsObjects(
        ingredients: ArrayList<String>,
        instructions: ArrayList<String>
    ) {
        ingredientsFragment = IngredientsFragment.newInstance(ingredients)
        instructionsFragment = InstructionsFragment.newInstance(instructions)
    }

    private fun initRecipeObjectFromParameter() {
        recipe = arguments?.getParcelable(RECIPE_OBJECT_PASSING_CODE)
    }

    private fun updateRecipeDetailsViews() {
        val minutes =
            recipe!!.totalTimeInMinutes.toString() + EMPTY_SPACE + getString(R.string.min)
        val numberOfIngredients =
            recipe!!.ingredients.size.toString() + EMPTY_SPACE + getString(R.string.ingredients)
        updateRecipeTextViewsWithTextDetails(minutes, numberOfIngredients)
        updateRecipeImageView()
    }

    private fun updateRecipeImageView() {
        Glide
            .with(this)
            .load(recipe!!.imageUrl)
            .into(binding.imageViewRecipe)
    }

    private fun updateRecipeTextViewsWithTextDetails(minuets: String, numberOfIngredients: String) {
        binding.apply {
            textViewRecipeName.text = recipe!!.name
            textViewNativeCountry.text = recipe!!.cuisine
            textViewTimeRequired.text = minuets
            textViewNumberOfIngredients.text = numberOfIngredients
            textViewEaseLevel.text = getString(R.string.easy)
        }
    }

    private fun setDefaultInnerFragment() {
        changeBetweenInstructionsAndIngredientsFragments(ingredientsFragment)
    }

    override fun addCallBacks() {
        binding.tabLayoutIngredientsInstructions.addOnTabSelectedListener(this)
        binding.imageButtonReturnBack.setOnClickListener {
            popBackStack(this)
        }
    }

    private fun popBackStack(fragment: Fragment) {
        val transaction = (activity as FragmentActivity).supportFragmentManager.beginTransaction()
        transaction.remove(fragment)
        transaction.commit()
    }

    private fun changeBetweenInstructionsAndIngredientsFragments(fragment: Fragment) {
        val transaction = (activity as FragmentActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container_instructions_ingredients, fragment)
        transaction.commit()
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        binding.apply {
            when (tab) {
                tabLayoutIngredientsInstructions.getTabAt(INGREDIENTS_TAB_INDEX) ->
                    changeBetweenInstructionsAndIngredientsFragments(ingredientsFragment)

                tabLayoutIngredientsInstructions.getTabAt(INSTRUCTIONS_TAB_INDEX) ->
                    changeBetweenInstructionsAndIngredientsFragments(instructionsFragment)
            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabReselected(tab: TabLayout.Tab?) {}

    companion object {
        private const val INGREDIENTS_TAB_INDEX = 0
        private const val INSTRUCTIONS_TAB_INDEX = 1
        private const val EMPTY_SPACE = " "
        const val RECIPE_OBJECT_PASSING_CODE = "RECIPE"

        fun newInstance(recipe: Recipe) =
            RecipeDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(RECIPE_OBJECT_PASSING_CODE, recipe)
                }
            }
    }

}



