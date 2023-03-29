package com.chocolatecake.indianfood.ui.search_recipes

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chocolatecake.indianfood.R
import com.chocolatecake.indianfood.databinding.ItemRecipeCategoryBinding
import com.chocolatecake.indianfood.model.Recipe

class RecipesSearchAdapter(
    private var recipes: List<Recipe>,
    private val onClickItem: (recipe: Recipe) -> Unit,
) : RecyclerView.Adapter<RecipesSearchAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_recipe_category, parent, false)
        )
    }

    fun setData(newRecipes: List<Recipe>) {
        recipes = newRecipes
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.binding.apply {
            textViewRecipeName.text = recipe.name
            textViewTotalTime.text = "${recipe.totalTimeInMinutes} min"
            textViewIngredientsCount.text = "${recipe.ingredients.size} ingredients"


            Glide.with(root).load(recipe.imageUrl).into(imageViewRecipe)

            root.setOnClickListener {
                onClickItem(recipes[position])
            }
        }
    }

    override fun getItemCount() = recipes.size


    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemRecipeCategoryBinding.bind(itemView)
    }
}