package com.chocolatecake.indianfood.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chocolatecake.indianfood.R
import com.chocolatecake.indianfood.databinding.ItemRecipeBinding
import com.chocolatecake.indianfood.model.Recipe

class RecipeAdapter(
    private val recipes: List<Recipe>,
    private val onClickRecipe: (recipe: Recipe) -> Unit,
) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val currentItem = recipes[position]
        holder.binding.apply {
            quickRecipesName.text = currentItem.name
            recipeCookingTime.text = currentItem.totalTimeInMinutes.toString()
            Glide.with(this.root.context).load(currentItem.imageUrl).into(quickRecipesImage)
            root.setOnClickListener { onClickRecipe(currentItem) }
        }
    }

    override fun getItemCount() = recipes.size


    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemRecipeBinding.bind(itemView)
    }

}