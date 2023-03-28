package com.chocolatecake.indianfood.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
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


    override fun setUp() {
        recipe = arguments?.getParcelable(RECIPE_OBJECT_PASSING_CODE)

    }

    override fun addCallBacks() {
        val itemsList: MutableList<DetailsViews<Any>> = mutableListOf()
        itemsList.add(DetailsViews(recipe!!, RecipeViewType.TYPE_HEADER))
        itemsList.addAll(recipe!!.ingredients.map { it.toDetailsItem() })
        binding.recipeDetailsRecycler.adapter = RecipeDetailsAdapter(itemsList,this)
        popBackStack(this)
    }
    override fun onTabSelected(tab: TabLayout.Tab?) {
        binding.apply {
            when (tab?.position) {
                0 -> {
                    val itemsList: MutableList<DetailsViews<Any>> = mutableListOf()
                    itemsList.add(DetailsViews(recipe!!, RecipeViewType.TYPE_HEADER))
                    itemsList.addAll(recipe!!.ingredients.map { it.toDetailsItem() })
                    val adapter = RecipeDetailsAdapter(itemsList,this@DetailsFragment)
                    recipeDetailsRecycler.adapter = adapter
                }
                1 -> {
                    val itemsList: MutableList<DetailsViews<Any>> = mutableListOf()
                    itemsList.add(DetailsViews(recipe!!, RecipeViewType.TYPE_HEADER))
                    itemsList.addAll(recipe!!.instruction.map { it.toDetailsItem() })
                    val adapter = RecipeDetailsAdapter(itemsList,this@DetailsFragment)
                    recipeDetailsRecycler.adapter = adapter

                }

            }
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        TODO("Not yet implemented")
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        TODO("Not yet implemented")
    }


    private fun popBackStack(fragment: Fragment) {
        val transaction = (activity as FragmentActivity).supportFragmentManager.beginTransaction()
        transaction.remove(fragment)
        transaction.commit()
    }


    companion object {
        const val RECIPE_OBJECT_PASSING_CODE = "RECIPE"

    }


}



