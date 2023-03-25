package com.chocolatecake.indianfood.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T>(
    private val itemsList: List<T>
) : RecyclerView.Adapter<BaseAdapter.BaseViewHolder>() {
    abstract val layoutId: Int


    override fun getItemCount(): Int = itemsList.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        BaseViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(layoutId, parent, false)
        )


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.binding
    }


    class BaseViewHolder(val binding: View) : RecyclerView.ViewHolder(binding)
}