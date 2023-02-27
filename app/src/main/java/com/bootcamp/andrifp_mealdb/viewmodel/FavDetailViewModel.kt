package com.bootcamp.andrifp_mealdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bootcamp.andrifp_mealdb.data.database.MealDatabase
import com.bootcamp.andrifp_mealdb.data.database.MealEntity
import com.bootcamp.andrifp_mealdb.data.network.LocalDataResource
import com.bootcamp.andrifp_mealdb.data.network.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavDetailViewModel(application: Application) : AndroidViewModel(application) {

    // DAO
    private val mealDao = MealDatabase.getDatabase(application).mealDao()
    private val local = LocalDataResource(mealDao)

    // Repository
    private val repository = Repository(local = local)

    fun deleteFavoriteMeal(mealEntity: MealEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local!!.deleteMeal(mealEntity)
        }
    }
}