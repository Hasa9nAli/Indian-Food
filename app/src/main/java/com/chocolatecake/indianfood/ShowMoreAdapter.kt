package com.chocolatecake.indianfood

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chocolatecake.indianfood.databinding.ItemMealBinding
import com.chocolatecake.indianfood.databinding.ShowMoreBinding
import com.chocolatecake.indianfood.model.Recipe

class ShowMoreAdapter(val recipies: List<Recipe>) :
    RecyclerView.Adapter<ShowMoreAdapter.ShowMoreViewHolder>() {

    companion object{
        const val UNIT_OF_TIME = "min"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowMoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
        return ShowMoreViewHolder(view)
    }

    override fun getItemCount(): Int = recipies.size


    override fun onBindViewHolder(holder: ShowMoreViewHolder, position: Int) {
        val currentCardMeal = recipies[position]
        setContentOfCard(holder, currentCardMeal)
    }

    private fun setContentOfCard(holder : ShowMoreViewHolder, currentCardMeal : Recipe) {
        holder.binding.apply {
            recipeName.text = currentCardMeal.name
            setTotalTimeOfCard(currentCardMeal, holder)
            Glide.with(recipeImage).load(currentCardMeal.imageUrl).into(recipeImage)
        }
    }
    private fun setTotalTimeOfCard(currentCardMeal : Recipe, holder : ShowMoreViewHolder){
        holder.binding.timeInMinute.text =
            checkValidTime(currentCardMeal.totalTimeInMinutes.toString())
    }

    private fun checkValidTime(time : String): String =
        if(time.trim() == "0") "instant" else "$time $UNIT_OF_TIME"


    class ShowMoreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemMealBinding.bind(view)

    }
}