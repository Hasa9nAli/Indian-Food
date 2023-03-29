package com.chocolatecake.indianfood.ui.show_more

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chocolatecake.indianfood.R
import com.chocolatecake.indianfood.databinding.ItemMealBinding
import com.chocolatecake.indianfood.model.Recipe

class ShowMoreAdapter(private val recipes: List<Recipe>) :
    RecyclerView.Adapter<ShowMoreAdapter.ShowMoreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowMoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
        return ShowMoreViewHolder(view)
    }

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: ShowMoreViewHolder, position: Int) {
        val currentCardMeal = recipes[position]
        holder.binding.apply {
            textViewRecipeName.text = currentCardMeal.name
            textViewTimeInMinutes.text =
                setValidTime(currentCardMeal.totalTimeInMinutes.toString(), root.context)
            Glide.with(imageViewRecipe).load(currentCardMeal.imageUrl).into(imageViewRecipe)
        }
    }

    private fun setValidTime(time: String, context: Context): String =
        if (time.trim() == "0") context.getString(R.string.instant_time)
        else context.getString(R.string.total_time_label, time)

    class ShowMoreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemMealBinding.bind(view)
    }
}