package com.chocolatecake.indianfood

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chocolatecake.indianfood.databinding.FragmentCategoriesBinding


class CategoriesFragment : Fragment() {
 private lateinit var  _binding:FragmentCategoriesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding =FragmentCategoriesBinding.inflate(inflater, container, false)
        return _binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding.cardView1.setOnClickListener { }
    }


}
