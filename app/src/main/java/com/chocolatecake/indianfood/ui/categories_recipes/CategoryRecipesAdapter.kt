package com.chocolatecake.indianfood.ui.categories_recipes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chocolatecake.indianfood.R
import com.chocolatecake.indianfood.databinding.ItemRecipeCategoryBinding
import com.chocolatecake.indianfood.model.Recipe

class CategoryRecipesAdapter(
    private val recipeList: List<Recipe>,
    private val onRecipeClick: (Recipe) -> Unit
) :
    RecyclerView.Adapter<CategoryRecipesAdapter.CategoryRecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CategoryRecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipe_category, parent, false)
        return CategoryRecipeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    override fun onBindViewHolder(holder: CategoryRecipeViewHolder, position: Int) {
        val currentRecipe = recipeList[position]
        holder.binding.apply {
            textViewIngredientsCount.text = root.context.getString(
                R.string.ingredients_label, currentRecipe.ingredients.size.toString()
            )
            textViewRecipeName.text = currentRecipe.name
            textViewTotalTime.text =
                root.context.getString(
                    R.string.minutes_label,
                    currentRecipe.totalTimeInMinutes.toString()
                )
            Glide.with(root.context).load(currentRecipe.imageUrl).into(imageViewRecipe)
            root.setOnClickListener { onRecipeClick(currentRecipe) }
        }
    }

    class CategoryRecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemRecipeCategoryBinding.bind(itemView)
    }
}