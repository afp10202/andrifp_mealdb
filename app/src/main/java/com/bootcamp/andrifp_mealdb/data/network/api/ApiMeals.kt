package com.bootcamp.andrifp_mealdb.data.network.api


import com.bootcamp.andrifp_mealdb.data.network.model.MealDetail
import com.bootcamp.andrifp_mealdb.data.network.model.MealList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiMeals {
    @GET("filter.php")
    suspend fun searchMeals(@Query("c") category: String): Response<MealList>

    @GET("lookup.php")
    suspend fun getMealDetail(@Query("i") mealId: String): Response<MealDetail>
}