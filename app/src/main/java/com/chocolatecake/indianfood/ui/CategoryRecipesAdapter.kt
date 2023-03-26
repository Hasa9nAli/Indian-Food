package com.chocolatecake.indianfood.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chocolatecake.indianfood.R
import com.chocolatecake.indianfood.databinding.ItemRecipeCategoryBinding
import com.chocolatecake.indianfood.model.Recipe

class CategoryRecipesAdapter(private val recipeList: List<Recipe>) :
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
        val recipe = recipeList[position]
        holder.binding.apply {
            txtIngredint.text = root.context.getString(
                R.string.ingredients_label, recipe.ingredients.size.toString()
            )
            txtRecipeName.text = recipe.name
            txtTotalTime.text =
                root.context.getString(R.string.minutes_label, recipe.totalTimeInMinutes.toString())
            Glide.with(root.context).load(recipe.imageUrl).into(imageViewImgRecipe)
        }
    }

    class CategoryRecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemRecipeCategoryBinding.bind(itemView)
    }

}
