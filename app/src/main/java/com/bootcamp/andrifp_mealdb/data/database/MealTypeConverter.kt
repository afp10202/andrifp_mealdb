package com.bootcamp.andrifp_mealdb.data.database

import androidx.room.TypeConverter
import com.bootcamp.andrifp_mealdb.data.network.model.MealsItem

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MealTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun mealDataToString(meal: MealsItem): String {
        return gson.toJson(meal)
    }

    @TypeConverter
    fun mealStringToData(string: String): MealsItem {
        val listType = object : TypeToken<MealsItem>() {}.type
        return gson.fromJson(string, listType)
    }
}