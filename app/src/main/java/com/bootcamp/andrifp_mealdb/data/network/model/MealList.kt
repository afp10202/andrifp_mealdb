package com.bootcamp.andrifp_mealdb.data.network.model

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class MealList(

	@field:SerializedName("meals")
	val meals: List<MealsItems?>? = null
) : Parcelable

@Parcelize
data class MealsItems(

	@field:SerializedName("strMealThumb")
	val strMealThumb: String? = null,

	@field:SerializedName("idMeal")
	val idMeal: String? = null,

	@field:SerializedName("strMeal")
	val strMeal: String? = null
) : Parcelable
