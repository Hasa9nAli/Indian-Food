package com.chocolatecake.indianfood.ui.recipe_details

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chocolatecake.indianfood.R
import com.chocolatecake.indianfood.databinding.ItemDetailsBinding
import com.chocolatecake.indianfood.databinding.ItemHeaderRecipeDetailsBinding
import com.chocolatecake.indianfood.model.DetailsViews
import com.chocolatecake.indianfood.model.Recipe
import com.chocolatecake.indianfood.util.RecipeViewType
import com.google.android.material.tabs.TabLayout

class RecipeDetailsAdapter(
    private var items: List<DetailsViews<Any>>,
    private var listner: TabLayout.OnTabSelectedListener
) :
    RecyclerView.Adapter<RecipeDetailsAdapter.BasicViewHolder>() {
    sealed class BasicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun setSelectedTabData(newItems: List<DetailsViews<Any>>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BasicViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_header_recipe_details, parent, false)
                RecipeDetailsHeaderViewHolder(view)
            }
            ITEM_INGREDIENT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_details, parent, false)
                IngredientsAndInstructionsDetailsViewHolder(view)
            }
            else -> throw Exception("UNKNOWN VIEW TYPE")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: BasicViewHolder,
        position: Int
    ) {
        when (holder) {
            is RecipeDetailsHeaderViewHolder -> bindHeader(holder, position)
            is IngredientsAndInstructionsDetailsViewHolder -> bindDetails(holder, position)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindHeader(holder: RecipeDetailsHeaderViewHolder, position: Int) {
        val currentItem = items[position].item as Recipe
        holder.binding.apply {
            tabLayoutIngredientsInstructions.addOnTabSelectedListener(listner)
            textViewRecipeName.text = currentItem.name
            textViewNativeCountry.text = currentItem.cuisine
            textViewTimeRequired.text = "${currentItem.totalTimeInMinutes} min"
            textViewNumberOfIngredients.text = "${currentItem.ingredients.size} ingredients"
            Glide.with(this.root.context).load(currentItem.imageUrl).into(imageViewRecipe)
        }
    }

    private fun bindDetails(holder: IngredientsAndInstructionsDetailsViewHolder, position: Int) {
        val currentItem = items[position].item as String
        holder.binding.textViewIngredientInstruction.text = currentItem
    }

    class RecipeDetailsHeaderViewHolder(itemView: View) : BasicViewHolder(itemView) {
        val binding = ItemHeaderRecipeDetailsBinding.bind(itemView)
    }

    class IngredientsAndInstructionsDetailsViewHolder(itemView: View) : BasicViewHolder(itemView) {
        val binding = ItemDetailsBinding.bind(itemView)
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position].type) {
            RecipeViewType.TYPE_HEADER -> TYPE_HEADER
            RecipeViewType.ITEM_INGREDIENT -> ITEM_INGREDIENT
        }
    }

    companion object {
        const val TYPE_HEADER = 1
        const val ITEM_INGREDIENT = 2
    }
}