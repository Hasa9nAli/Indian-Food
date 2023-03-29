package com.chocolatecake.indianfood.ui.recipe_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.chocolatecake.indianfood.dataSource.utils.toDetailsItem
import com.chocolatecake.indianfood.databinding.FragmentDetailsBinding
import com.chocolatecake.indianfood.model.DetailsViews
import com.chocolatecake.indianfood.model.Recipe
import com.chocolatecake.indianfood.ui.base.BaseFragment
import com.chocolatecake.indianfood.util.RecipeViewType
import com.google.android.material.tabs.TabLayout


class DetailsFragment : BaseFragment<FragmentDetailsBinding>(), TabLayout.OnTabSelectedListener {

    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailsBinding =
        FragmentDetailsBinding::inflate

    private var recipe: Recipe? = null
    private lateinit var recipeDetailsAdapter: RecipeDetailsAdapter


    override fun setUp() {
        recipe = arguments?.getParcelable(RECIPE_OBJECT_PASSING_CODE)
        val itemsList: MutableList<DetailsViews<Any>> = mutableListOf()
        itemsList.add(DetailsViews(recipe!!, RecipeViewType.TYPE_HEADER))
        itemsList.addAll(recipe!!.ingredients.map { it.toDetailsItem() })
        recipeDetailsAdapter = RecipeDetailsAdapter(itemsList, this)
        binding.recipeDetailsRecycler.adapter = recipeDetailsAdapter
    }

    override fun addCallBacks() {
        binding.appBar.buttonBack.setOnClickListener { parentFragmentManager.popBackStack() }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {

        binding.apply {
            when (tab?.position) {
                0 -> {
                    val itemsList: MutableList<DetailsViews<Any>> = mutableListOf()
                    itemsList.add(DetailsViews(recipe!!, RecipeViewType.TYPE_HEADER))
                    itemsList.addAll(recipe!!.ingredients.map { it.toDetailsItem() })
                    recipeDetailsAdapter.setSelectedTabData(itemsList)
                }
                1 -> {
                    val itemsList: MutableList<DetailsViews<Any>> = mutableListOf()
                    itemsList.add(DetailsViews(recipe!!, RecipeViewType.TYPE_HEADER))
                    itemsList.addAll(recipe!!.instruction.map { it.toDetailsItem() })
                    recipeDetailsAdapter.setSelectedTabData(itemsList)
                }
            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

    companion object {
        const val RECIPE_OBJECT_PASSING_CODE = "RECIPE"

        fun newInstance(recipe: Recipe) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(RECIPE_OBJECT_PASSING_CODE, recipe)
                }
            }
    }
}