package com.chocolatecake.indianfood.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chocolatecake.indianfood.R
import com.chocolatecake.indianfood.databinding.FragmentCategoriesRecpecBinding
import com.chocolatecake.indianfood.interactor.GetBreakfastRecipesInteractor
import com.chocolatecake.indianfood.interactor.GetDinnerRecipesInteractor
import com.chocolatecake.indianfood.interactor.GetLunchRecipesInteractor
import com.chocolatecake.indianfood.model.Recipe

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CategoriesRecipesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoriesRecipesFragment :BaseFragment<FragmentCategoriesRecpecBinding>(){
    // TODO: Rename and change types of parameters
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCategoriesRecpecBinding
        get() = TODO("Not yet implemented")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories_recpec, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         var recipe: List<Recipe>? =null
        when(param1) {
            GetBreakfastRecipesInteractor.BREAKFAST -> recipe
            GetLunchRecipesInteractor.LUNCH -> recipe
            GetDinnerRecipesInteractor.DINNER -> recipe

        }
    }

    override fun setUp() {
        TODO("Not yet implemented")
    }

    override fun addCallBacks() {
        TODO("Not yet implemented")
    }


    companion object {


        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CategoriesRecpecFragment.
         */
        // TODO: Rename and change types and number of parameters

        fun newInstance(param1: String) =
            CategoriesRecipesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}