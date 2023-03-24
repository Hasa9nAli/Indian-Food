package com.chocolatecake.indianfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chocolatecake.indianfood.databinding.ItemMealBinding
import com.chocolatecake.indianfood.ui.BaseFragment
import java.io.Serializable

class ItemMealFragment(): BaseFragment<ItemMealBinding>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemMealBinding = ItemMealBinding::inflate



    override fun setUp() {
    }

    override fun addCallBacks() {
    }

    override fun onStart() {
        super.onStart()
        val data = arguments?.getSerializable("dataList") as List<String>
            val name = data[0]
            val time = "${data[1]} min"
            val imageUrl = data[2]

        binding.recipeName.text = name
        binding.timeInMinute.text = time
    }

    companion object{
        fun newInstance(data:List<String>):ItemMealFragment{
            val bundle = Bundle().apply {
                putSerializable("dataList", data as Serializable)
            }
            val fragment = ItemMealFragment().apply {
                arguments = bundle
            }

            return fragment
        }
    }
}