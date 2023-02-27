package com.bootcamp.andrifp_mealdb.data.network

import com.bootcamp.andrifp_mealdb.data.network.api.ApiMeals
import com.bootcamp.andrifp_mealdb.utils.Constant.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Service {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val mealService: ApiMeals by lazy {
        retrofit.create(ApiMeals::class.java)
    }
}