package com.chocolatecake.indianfood.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chocolatecake.indianfood.R
import com.chocolatecake.indianfood.databinding.ItemRandomRecipeBinding
import com.chocolatecake.indianfood.databinding.ItemSectionBinding
import com.chocolatecake.indianfood.databinding.LayoutRecipesBinding
import com.chocolatecake.indianfood.model.HomeItem
import com.chocolatecake.indianfood.model.Recipe
import com.chocolatecake.indianfood.util.HomeItemType

class HomeAdapter(
    private val items: List<HomeItem<Any>>,
    private val onClickShowMore: (categoryType: String) -> Unit,
    private val onClickRecipe: (recipe: Recipe) -> Unit,
) :
    RecyclerView.Adapter<HomeAdapter.BasicViewHolder>() {

    sealed class BasicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasicViewHolder {
        return when (viewType) {
            ITEM_TYPE_RANDOM_RECIPES -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_random_recipe, parent, false)
                RandomRecipeViewHolder(view)
            }

            ITEM_TYPE_SECTION -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_section, parent, false)
                SectionViewHolder(view)
            }

            ITEM_TYPE_RECIPES -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_recipes, parent, false)
                RecipesViewHolder(view)
            }

            else -> throw Exception("UNKNOWN VIEW TYPE")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BasicViewHolder, position: Int) {
        when (holder) {
            is RandomRecipeViewHolder -> bindRandomRecipe(holder, position)
            is SectionViewHolder -> bindSection(holder, position)
            is RecipesViewHolder -> bindRecipes(holder, position)
        }
    }

    private fun bindRecipes(holder: RecipesViewHolder, position: Int) {
        val currentRecipes = items[position].item as List<Recipe>
        val adapter = RecipesAdapter(currentRecipes, onClickRecipe)
        holder.binding.recyclerViewRecipes.adapter = adapter
    }

    private fun bindSection(holder: SectionViewHolder, position: Int) {
        val currentSection = items[position].item as String
        holder.binding.apply {
            textTitle.text = currentSection
            showMore.setOnClickListener { onClickShowMore(currentSection) }
        }
    }

    private fun bindRandomRecipe(holder: RandomRecipeViewHolder, position: Int) {
        val currentRandomRecipe = items[position].item as Recipe
        holder.binding.apply {
            textViewRecipeCookingTime.text =
                setValidTime(currentRandomRecipe.totalTimeInMinutes.toString(), root.context)
            textViewRecipeCuisine.text = currentRandomRecipe.cuisine
            textViewRecipeName.text = currentRandomRecipe.name
            Glide.with(this.root.context).load(currentRandomRecipe.imageUrl).into(dishOfTheDayImage)
            root.setOnClickListener { onClickRecipe(currentRandomRecipe) }
        }
    }

    private fun setValidTime(time: String, context: Context): String =
        if (time.trim() == "0") context.getString(R.string.instant_time)
        else context.getString(R.string.total_time_label, time)

    class RandomRecipeViewHolder(itemView: View) : BasicViewHolder(itemView) {
        val binding = ItemRandomRecipeBinding.bind(itemView)
    }

    class RecipesViewHolder(itemView: View) : BasicViewHolder(itemView) {
        val binding = LayoutRecipesBinding.bind(itemView)
    }

    class SectionViewHolder(itemView: View) : BasicViewHolder(itemView) {
        val binding = ItemSectionBinding.bind(itemView)
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position].type) {
            HomeItemType.TYPE_RANDOM_RECIPES -> ITEM_TYPE_RANDOM_RECIPES
            HomeItemType.TYPE_SECTION -> ITEM_TYPE_SECTION
            HomeItemType.TYPE_RECIPE -> ITEM_TYPE_RECIPES
        }
    }


    companion object {
        const val ITEM_TYPE_RANDOM_RECIPES = 1
        const val ITEM_TYPE_SECTION = 2
        const val ITEM_TYPE_RECIPES = 3
    }
}