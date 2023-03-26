package com.chocolatecake.indianfood.ui.search
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.chocolatecake.indianfood.R
import com.chocolatecake.indianfood.dataSource.CsvDataSource
import com.chocolatecake.indianfood.dataSource.utils.CsvParser
import com.chocolatecake.indianfood.databinding.FragmentIngredientsSearchBinding
import com.chocolatecake.indianfood.interactor.*
import com.chocolatecake.indianfood.model.Recipe
import com.chocolatecake.indianfood.ui.RecipeDetailsFragment
import com.chocolatecake.indianfood.ui.base.BaseFragment
import com.chocolatecake.indianfood.util.Constants.INSTRUCTIONS_TAB_INDEX
import com.chocolatecake.indianfood.util.Constants.RECIPE_TAB_INDEX
import com.chocolatecake.indianfood.util.ItemListener
import com.chocolatecake.indianfood.util.navigateTo
import com.google.android.material.chip.Chip

class IngredientsSearchFragment : BaseFragment<FragmentIngredientsSearchBinding>(), ItemListener {
    private lateinit var dataSource: IndianFoodDataSource
    private lateinit var findRecipesContainsSpecifiedIngredient: FindRecipesContainsSpecifiedIngredientInteractor
    private lateinit var findRecipesByName: FindRecipesByNameInteractor
    private lateinit var csvParser: CsvParser
    private var ingredientsList = mutableListOf("oil")
    private var recipeName  = ""
    private lateinit var getRandomMeals : GetRandomMealsIntractor
    private var chipNext = ""
    var tap = 3


    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentIngredientsSearchBinding
        get() = FragmentIngredientsSearchBinding::inflate


    override fun setUp() {
        setupDatasource()
        setSearchOnClickListener()
        checkIfRecipeOrIngredient()
       // setChipOnClickListener()
    }

    private fun setupDatasource() {
        csvParser = CsvParser()
        dataSource = CsvDataSource(csvParser, requireContext())
        findRecipesContainsSpecifiedIngredient = FindRecipesContainsSpecifiedIngredientInteractor(dataSource)
        findRecipesByName = FindRecipesByNameInteractor(dataSource)
        getRandomMeals = GetRandomMealsIntractor(dataSource)

    }

    private fun checkIfRecipeOrIngredient() {
        when (tap.toString()) {
            RECIPE_TAB_INDEX -> {
                setRandomMeals()
                getRecipes(recipeName)
              //  binding.noDataFound.error.visibility = View.VISIBLE
            }
            INSTRUCTIONS_TAB_INDEX -> {
                getInstructions(ingredientsList)
            }
        }
    }



    private fun getInstructions( ingredientsList: List<String>) {
        try {
            val ingredients = findRecipesContainsSpecifiedIngredient.invoke(ingredientsList.distinct())

            if (ingredients.isNotEmpty()) {
                setUpAdapter(ingredients)
            } else {
                binding.noDataFound.error.visibility = View.VISIBLE
            }
        } catch (e: IllegalAccessException) {
            showToast(message = e.message.toString())
        }
    }

    private fun getRecipes(searchText: String) {
        try {
            val recipes = findRecipesByName.invoke(searchName = searchText)

            if (recipes.isNotEmpty() || recipeName != "") {
                setUpAdapter(recipes)
            } else {
                binding.noDataFound.error.visibility = View.VISIBLE
            }
        } catch (e: IllegalAccessException) {
            showToast(message = e.message.toString())
        }
    }


    private fun setRandomMeals() {
        try {
            val randomMeals = getRandomMeals.invoke().take(4)
            if ( randomMeals.isNotEmpty()) {
                createChips(randomMeals)
            } else {
                binding.noDataFound.error.visibility = View.VISIBLE
            }
        } catch (e: IllegalAccessException) {
            showToast(message = e.message.toString())
        }

    }


    private fun setUpAdapter(recipe: List<Recipe>) {
        val searchAdapter = SearchAdapter(recipe, this)
        binding.searchRecyclerView.adapter = searchAdapter
    }


    override fun onClickItem(recipe: Recipe) {
        requireActivity().navigateTo(RecipeDetailsFragment.newInstance(recipe))
    }


    override fun addCallBacks() {
        when (tap.toString()) {
            RECIPE_TAB_INDEX -> {
                onChoiceChip()
            }
            INSTRUCTIONS_TAB_INDEX -> {
                onChoiceChips()
            }
        }
    }


    private fun onChoiceChip(){
        binding.chipsgroup.chipGroup.setOnCheckedStateChangeListener {
            group , checkedId ->
            val  chip:Chip = group.findViewById(checkedId[checkedId.lastIndex])

            chip.let {
                recipeName = it.text.toString()
                showToast(message = it.text.toString())
                getRecipes(recipeName)
            }
        }

    }

    private fun onChoiceChips(){
        binding.chipsgroup.chipGroup.setOnCheckedStateChangeListener {
                group , checkedId ->
            val  chip:Chip = group.findViewById(checkedId[checkedId.lastIndex])

            chip.let {
                ingredientsList.add(it.text.toString())
                showToast(message = it.text.toString())
                getInstructions(ingredientsList)
            }
            chip.setOnCloseIconClickListener {
                 binding.chipsgroup.chipGroup.removeView(it)
                 ingredientsList.remove(it.toString())
                }
        }

    }


    private fun setSearchOnClickListener() {

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit( query: String?): Boolean {

                when (tap.toString()) {
                    RECIPE_TAB_INDEX -> {
                        recipeName = query!!
                        getRecipes(query)
                    }
                    INSTRUCTIONS_TAB_INDEX -> {
                        ingredientsList.add(query!!)
                        createChip(query)
                        getInstructions(ingredientsList)
                    }
                }

                binding.searchView.clearFocus()

                return false

            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }
//
    private fun createChips(items: List<String> ) {

        for (chipText in items) {
            val chip = Chip(context)
            chip.text = chipText
            chip.isClickable = true
            chip.isCheckable = true
            binding.chipsgroup.chipGroup.addView(chip)
        }
    }

    private fun createChip(chipNext: String ) {
            val chip = Chip(context)
            chip.text = chipNext
            chip.isClickable = true
            chip.isCheckable = true
            chip.isChipIconVisible = false
            chip.isCloseIconVisible = true
            binding.chipsgroup.chipGroup.addView(chip)
    }


    companion object {
        fun newInstance(index: Int) =
            RecipeDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(RECIPE_TAB_INDEX, index)
                    putInt(INSTRUCTIONS_TAB_INDEX, index)
                }
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}





//    fun createChip(name: String) {
//        val chip = Chip(context)
//        chip.apply {
//            text = name
//            chipIcon = ContextCompat.getDrawable(
//                context,
//                R.drawable.ic_launcher_background
//            )
//            isChipIconVisible = false
//            isCloseIconVisible = true
//            isCheckable = true
//            isClickable = true
//            binding.apply {
//                chipsgroup.chipGroup.addView(chip as View)
//                chip.setOnCloseIconClickListener {
//                    chipsgroup.chipGroup.removeView(it)
//                    ingredientsList.remove(it.toString())
//                }
//            }
//        }
//    }


//    chipGroup.setOnCheckedChangeListener { group, checkedId ->
//        val selectedChips = group.checkedChipIds
//        for (chipId in selectedChips) {
//            val chip = findViewById<Chip>(chipId)
//            // Do something with the selected chip
//        }
//    }



//    private fun findRecipesByNameInteractorScreen() {
//        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                query?.let { getRecipes(it) }
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return false
//            }
//        })
//    }

//    private fun setChipOnClickListener() {
//        binding.chipsgroup.chipGroup.setOnClickListener() { view ->
//            recipeName = (view.parent as Chip).text.toString()
//            showToast(message = recipeName)
//            ingredientsList.add((view.parent as Chip).text.toString())
//            checkIfRecipeOrIngredient()
////            getRecipes(recipeName)
//            // binding.searchView.setQuery(recipeName, false)
//        }
//    }