package com.chocolatecake.indianfood.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chocolatecake.indianfood.R
import com.chocolatecake.indianfood.databinding.ItemRandomRecipesBinding
import com.chocolatecake.indianfood.databinding.LayoutRecipesBinding
import com.chocolatecake.indianfood.databinding.LayoutTextInHomeBinding
import com.chocolatecake.indianfood.model.HomeItem
import com.chocolatecake.indianfood.model.Recipe
import com.chocolatecake.indianfood.util.HomeItemType

class HomeAdapter(
    private val items: List<HomeItem<Any>>,
    private val showMoreListener: OnClickShowMore,
    private val recipeListener: OnClickRecipe,
    private val randomRecipes: OnClickRandomRecipe
) :
    RecyclerView.Adapter<HomeAdapter.BasicViewHolder>() {


    sealed class BasicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasicViewHolder {
        return when (viewType) {
            ITEM_TYPE_RANDOM_RECIPES -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_random_recipes, parent, false)
                RandomRecipesViewHolder(view)
            }
            ITEM_TYPE_TEXT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_text_in_home, parent, false)
                TextViewHolder(view)
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
            is RandomRecipesViewHolder -> bindRandomRecipes(holder, position)
            is TextViewHolder -> bindTitleSection(holder, position)
            is RecipesViewHolder -> bindRecipes(holder, position)
        }
    }

    private fun bindRecipes(holder: RecipesViewHolder, position: Int) {
        val recipes = items[position].item as List<Recipe>
        Log.e("TAG", "bindRecipes: $recipes")
        val adapter = RecipeAdapter(recipes, recipeListener)
        holder.binding.recipiesRecyclerView.adapter = adapter
    }

    private fun bindTitleSection(holder: TextViewHolder, position: Int) {
        val currentItems = items[position].item as String
        holder.binding.apply {
            textTitle.text = currentItems
            showMore.setOnClickListener {
                showMoreListener.onClickShowMore()
            }
        }
    }

    private fun bindRandomRecipes(holder: RandomRecipesViewHolder, position: Int) {
        val currentItems = items[position].item as Recipe
        holder.binding.apply {
            RecipeCookingTime.text = currentItems.totalTimeInMinutes.toString()
            RecipeCuisine.text = currentItems.cuisine
            RecipeName.text = currentItems.name
            Glide.with(this.root.context).load(currentItems.imageUrl).into(dishOfTheDayImage)
            root.setOnClickListener { randomRecipes.onClickRandomRecipe() }
        }
    }


    class RandomRecipesViewHolder(itemView: View) : BasicViewHolder(itemView) {
        val binding = ItemRandomRecipesBinding.bind(itemView)
    }

    class RecipesViewHolder(itemView: View) : BasicViewHolder(itemView) {
        val binding = LayoutRecipesBinding.bind(itemView)
    }

    class TextViewHolder(itemView: View) : BasicViewHolder(itemView) {
        val binding = LayoutTextInHomeBinding.bind(itemView)
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position].type) {
            HomeItemType.RANDOM_RECIPES -> ITEM_TYPE_RANDOM_RECIPES
            HomeItemType.TEXT -> ITEM_TYPE_TEXT
            HomeItemType.RECIPE -> ITEM_TYPE_RECIPES
        }
    }


    companion object {
        const val ITEM_TYPE_RANDOM_RECIPES = 1
        const val ITEM_TYPE_TEXT = 2
        const val ITEM_TYPE_RECIPES = 3
    }
}

interface OnClickShowMore {
    fun onClickShowMore()
}

interface OnClickRandomRecipe {
    fun onClickRandomRecipe()
}

