//package com.chocolatecake.indianfood.ui.search
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.chocolatecake.indianfood.R
//import com.chocolatecake.indianfood.databinding.CardSearchItemBinding
//import com.chocolatecake.indianfood.model.Recipe
//
//class SearchAdapter(
//    private val recipes: List<Recipe>,
//    private val onClickItem: (recipe: Recipe) -> Unit ,
//    ) : RecyclerView.Adapter<SearchAdapter.RecipeViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
//        return RecipeViewHolder(
//            LayoutInflater
//                .from(parent.context)
//                .inflate(R.layout.card_search_item, parent, false)
//        )
//    }
//
//    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
//        val recipe = recipes[position]
//        holder.binding.apply {
//            recipeName.text = recipe.name
//            recipeTime.text = "${recipe.totalTimeInMinutes} min"
//            recipeIngredientsCount.text = "${recipe.ingredients.size} ingredients"
//
//
//            Glide.with(root).load(recipe.imageUrl).into(recipeImage)
//
//            recipeItem.setOnClickListener {
//                onClickItem(recipes[position])
//            }
//        }
//    }
//
//    override fun getItemCount() = recipes.size
//
//
//    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val binding = CardSearchItemBinding.bind(itemView)
//    }
//}