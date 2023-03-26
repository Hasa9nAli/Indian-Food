package com.chocolatecake.indianfood.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chocolatecake.indianfood.databinding.ItemRecipeFragmentBinding
import com.chocolatecake.indianfood.model.Recipe

class CategoryRecipesAdapter(val context: Context, val recipeList: List<Recipe>) :
    RecyclerView.Adapter<CategoryRecipesAdapter.CategoryRecipeViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = CategoryRecipeViewHolder(
        ItemRecipeFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int {
        return recipeList.size
    }

    override fun onBindViewHolder(holder: CategoryRecipeViewHolder, position: Int) {
        val recipe = recipeList[position]
        holder.apply {
            txtIngredint.text = recipe.ingredients.size.toString()+" ingredients"
            txtRecipeName.text = recipe.name.split("-")[0]
            txtTotalTime.text = recipe.totalTimeInMinutes.toString() + " Min"
            Glide.with(context).apply {
                load(recipe.imageUrl).into(imgRecipeCard)
            }
        }
    }

    class CategoryRecipeViewHolder(binding: ItemRecipeFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imgRecipeCard = binding.imgRecipeCard
        val txtRecipeName = binding.txtRecipeName
        val txtIngredint = binding.txtIngredint
        val txtTotalTime = binding.txtTotalTime
    }


}
