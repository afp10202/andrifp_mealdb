package com.bootcamp.andrifp_mealdb.data.network

import com.bootcamp.andrifp_mealdb.data.database.MealDao
import com.bootcamp.andrifp_mealdb.data.database.MealEntity
import kotlinx.coroutines.flow.Flow

class LocalDataResource(private val dao: MealDao) {
    suspend fun insertMeal(mealEntity: MealEntity) = dao.insertMeal(mealEntity)
    fun listMeal(): Flow<List<MealEntity>> = dao.listMeal()
    suspend fun deleteMeal(mealEntity: MealEntity) = dao.deleteMeal(mealEntity)
    suspend fun deleteAllMeals() = dao.deleteAllMeals()
}