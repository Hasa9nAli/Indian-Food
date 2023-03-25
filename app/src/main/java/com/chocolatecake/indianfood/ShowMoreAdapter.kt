package com.chocolatecake.indianfood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chocolatecake.indianfood.databinding.ShowMoreBinding
import com.chocolatecake.indianfood.model.Recipe

class ShowMoreAdapter(val recipies: List<Recipe>) :
    RecyclerView.Adapter<ShowMoreAdapter.ShowMoreViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowMoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.show_more, parent, false)
        return ShowMoreViewHolder(view)
    }

    override fun getItemCount(): Int = recipies.size

    override fun onBindViewHolder(holder: ShowMoreViewHolder, position: Int) {

    }

    class ShowMoreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ShowMoreBinding.bind(view)

    }
}