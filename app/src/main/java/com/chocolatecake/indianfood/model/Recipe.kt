package com.chocolatecake.indianfood.model

import android.os.Parcel
import android.os.Parcelable


data class Recipe(
    val name: String,
    val ingredients: List<String>,
    val totalTimeInMinutes: Int,
    val cuisine: String,
    val instruction: List<String>,
    val sourceUrl: String,
    val cleanedIngredients: List<String>,
    val imageUrl: String,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        name = parcel.readString() ?: "",
        ingredients = parcel.createStringArrayList() ?: emptyList(),
        totalTimeInMinutes = parcel.readInt() ,
        cuisine = parcel.readString() ?:"",
        instruction = parcel.createStringArrayList() ?: emptyList(),
        sourceUrl = parcel.readString()?:"",
        cleanedIngredients = parcel.createStringArrayList() ?: emptyList(),
        imageUrl = parcel.readString()?:""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeStringList(ingredients)
        parcel.writeInt(totalTimeInMinutes)
        parcel.writeString(cuisine)
        parcel.writeStringList(instruction)
        parcel.writeString(sourceUrl)
        parcel.writeStringList(cleanedIngredients)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Recipe> {
        override fun createFromParcel(parcel: Parcel): Recipe {
            return Recipe(parcel)
        }

        override fun newArray(size: Int): Array<Recipe?> {
            return arrayOfNulls(size)
        }
    }

}