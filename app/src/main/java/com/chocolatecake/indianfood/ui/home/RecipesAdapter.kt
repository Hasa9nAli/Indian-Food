package com.chocolatecake.indianfood.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chocolatecake.indianfood.R
import com.chocolatecake.indianfood.databinding.ItemRecipeBinding
import com.chocolatecake.indianfood.model.Recipe

class RecipesAdapter(
    private val recipes: List<Recipe>,
    private val onClickRecipe: (recipe: Recipe) -> Unit,
) :
    RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val currentItem = recipes[position]
        holder.binding.apply {
            textViewQuickRecipesName.text = currentItem.name
            textViewTimeInMinutes.text =
                setValidTime(currentItem.totalTimeInMinutes.toString(), root.context)
            Glide.with(this.root.context).load(currentItem.imageUrl).into(imageViewQuickRecipe)
            root.setOnClickListener { onClickRecipe(currentItem) }
        }
    }

    private fun setValidTime(time: String, context: Context): String =
        if (time.trim() == "0") context.getString(R.string.instant_time)
        else context.getString(R.string.total_time_label, time)

    override fun getItemCount() = recipes.size

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemRecipeBinding.bind(itemView)
    }
}