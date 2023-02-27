package com.bootcamp.andrifp_mealdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bootcamp.andrifp_mealdb.data.database.MealDatabase
import com.bootcamp.andrifp_mealdb.data.database.MealEntity
import com.bootcamp.andrifp_mealdb.data.network.LocalDataResource
import com.bootcamp.andrifp_mealdb.data.network.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavViewModel(application: Application) : AndroidViewModel(application) {
    // LOCAL
    private val mealDao = MealDatabase.getDatabase(application).mealDao()
    private val local = LocalDataResource(mealDao)

    private val repository = Repository(local = local)

    val favoriteMealList: LiveData<List<MealEntity>> = repository.local!!.listMeal().asLiveData()

    fun deleteAllMeals() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local!!.deleteAllMeals()
        }
    }
}