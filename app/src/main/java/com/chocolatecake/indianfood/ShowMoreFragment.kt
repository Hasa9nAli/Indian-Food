package com.chocolatecake.indianfood

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import com.chocolatecake.indianfood.databinding.ShowMoreBinding
import com.chocolatecake.indianfood.model.Recipe
import com.chocolatecake.indianfood.ui.BaseFragment

class ShowMoreFragment() : BaseFragment<ShowMoreBinding>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> ShowMoreBinding =
        ShowMoreBinding::inflate


    override fun setUp() {
        goBack()
    }

    private fun goBack() {

        val arrowBackButton = binding.arrowBack.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
    }

    override fun addCallBacks() {

    }

    val recipies = listOf(
        Recipe(
            name = "Spinach and Feta Stuffed Chicken",
            ingredients = listOf(
                "chicken breasts",
                "spinach",
                "feta cheese",
                "garlic",
                "olive oil"
            ),
            totalTimeInMinutes = 40,
            cuisine = "Mediterranean",
            instruction = listOf(
                "Preheat the oven to 375°F (190°C).",
                "Flatten chicken breasts using a meat mallet or rolling pin.",
                "In a bowl, mix spinach, feta cheese, garlic, and olive oil.",
                "Divide the mixture between the chicken breasts and roll up tightly.",
                "Place the stuffed chicken breasts in a baking dish.",
                "Bake for 25-30 minutes or until cooked through.",
            ),
            sourceUrl = "https://www.example.com/spinach-feta-stuffed-chicken",
            cleanedIngredients = listOf(
                "chicken breasts",
                "spinach",
                "feta cheese",
                "garlic",
                "olive oil"
            ),
            imageUrl = "https://www.example.com/spinach-feta-stuffed-chicken.jpg",
        ),
        Recipe(
            name = "Beef and Broccoli Stir Fry",
            ingredients = listOf(
                "beef sirloin",
                "broccoli florets",
                "soy sauce",
                "brown sugar",
                "garlic"
            ),
            totalTimeInMinutes = 20,
            cuisine = "Asian",
            instruction = listOf(
                "Slice beef into thin strips.",
                "In a pan, cook beef until browned.",
                "Add broccoli and cook for 3-4 minutes.",
                "In a separate bowl, mix soy sauce, brown sugar, and garlic.",
                "Add the soy sauce mixture to the pan and cook until heated through.",
            ),
            sourceUrl = "https://www.example.com/beef-broccoli-stir-fry",
            cleanedIngredients = listOf(
                "beef sirloin",
                "broccoli florets",
                "soy sauce",
                "brown sugar",
                "garlic"
            ),
            imageUrl = "https://www.example.com/beef-broccoli-stir-fry.jpg",
        ),
        Recipe(
            name = "Mushroom Risotto",
            ingredients = listOf(
                "arborio rice",
                "mushrooms",
                "onion",
                "white wine",
                "parmesan cheese"
            ),
            totalTimeInMinutes = 45,
            cuisine = "Italian",
            instruction = listOf(
                "In a pot, bring chicken broth to a simmer.",
                "In a separate pan, cook mushrooms and onion until softened.",
                "Add arborio rice and cook for 1-2 minutes.",
                "Add white wine and cook until absorbed.",
                "Gradually add the chicken broth, stirring frequently, until the rice is cooked and the broth is absorbed.",
                "Stir in parmesan cheese and serve.",
            ),
            sourceUrl = "https://www.example.com/mushroom-risotto",
            cleanedIngredients = listOf(
                "arborio rice",
                "mushrooms",
                "onion",
                "white wine",
                "parmesan cheese"
            ),
            imageUrl = "https://www.example.com/mushroom-risotto.jpg",
        ),
    )

    override fun onStart() {
        super.onStart()
        val gridLayout: GridLayout = binding.grid
        for (recipie in recipies) {
            val itemMealFragment = ItemMealFragment
                .newInstance(
                    listOf<String>(
                        recipie.name,
                        recipie.totalTimeInMinutes.toString(),
                        recipie.imageUrl
                    )
                )
            parentFragmentManager.beginTransaction().add(gridLayout.id, itemMealFragment).commit()

        }
    }
}