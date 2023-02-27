package com.bootcamp.andrifp_mealdb.data.network

import android.util.Log
import com.bootcamp.andrifp_mealdb.data.network.api.ApiMeals
import com.bootcamp.andrifp_mealdb.data.network.handler.NetworkResult
import com.bootcamp.andrifp_mealdb.data.network.model.MealDetail
import com.bootcamp.andrifp_mealdb.data.network.model.MealList

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val ApiMeals: ApiMeals) {
    suspend fun getMeals(): Flow<NetworkResult<MealList>> = flow {
        try {
            emit(NetworkResult.Loading(true))

            val meals = ApiMeals.searchMeals("Seafood")
            if (meals.isSuccessful) {
                val responseBody = meals.body()
                if (responseBody?.meals?.isEmpty() == true) {
                    emit(NetworkResult.Error("Meals not found"))
                } else {
                    emit(NetworkResult.Success(responseBody!!))
                }
            } else {
                Log.d("apiRemoteError", "StatusCode: ${meals.code()}, message: ${meals.message()}")
                emit(NetworkResult.Error("Failed to fetch from server."))
            }
        } catch (err: Exception) {
            err.printStackTrace()
            Log.d("remoteError", "${err.message}")
            emit(NetworkResult.Error("Something went wrong, Please check log."))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getMealDetail(mealId: String): Flow<NetworkResult<MealDetail>> = flow {
        try {
            emit(NetworkResult.Loading(true))

            val meal = ApiMeals.getMealDetail(mealId)
            if (meal.isSuccessful) {
                val responseBody = meal.body()

                if (responseBody?.meals?.isEmpty() == true) {
                    emit(NetworkResult.Error("Meals not found"))
                } else {
                    emit(NetworkResult.Success(responseBody!!))
                }
            } else {
                Log.d("apiRemoteError", "StatusCode: ${meal.code()}, message: ${meal.message()}")
                emit(NetworkResult.Error("Failed to fetch from server."))
            }
        } catch (err: Exception) {
            err.printStackTrace()
            Log.d("remoteError", "${err.message}")
            emit(NetworkResult.Error("Something went wrong, Please check log."))
        }
    }.flowOn(Dispatchers.IO)
}