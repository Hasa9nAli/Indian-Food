package com.chocolatecake.indianfood

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chocolatecake.indianfood.databinding.FragmentCategoriesBinding
import com.chocolatecake.indianfood.ui.BaseFragment


class CategoriesFragment :BaseFragment<FragmentCategoriesBinding>(){

 private lateinit var  _binding:FragmentCategoriesBinding
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCategoriesBinding=
        FragmentCategoriesBinding::inflate


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding =FragmentCategoriesBinding.inflate(inflater, container, false)
        return _binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       binding.cardViewBreakfast.setOnClickListener {
           val transaction=parentFragmentManager.beginTransaction()
           transaction.add(R.id.fragment_container,this)
           transaction.commit()


       }
    }




    override fun setUp() {
    }

    override fun addCallBacks() {
    }
}

