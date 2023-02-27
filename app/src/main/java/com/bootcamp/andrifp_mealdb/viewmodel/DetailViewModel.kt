package com.bootcamp.andrifp_mealdb.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.bootcamp.andrifp_mealdb.data.database.MealDatabase
import com.bootcamp.andrifp_mealdb.data.database.MealEntity
import com.bootcamp.andrifp_mealdb.data.network.LocalDataResource
import com.bootcamp.andrifp_mealdb.data.network.RemoteDataSource
import com.bootcamp.andrifp_mealdb.data.network.Repository
import com.bootcamp.andrifp_mealdb.data.network.Service
import com.bootcamp.andrifp_mealdb.data.network.handler.NetworkResult
import com.bootcamp.andrifp_mealdb.data.network.model.MealDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    // API
    private val remoteService = Service.mealService
    private val remote = RemoteDataSource(remoteService)

    // DAO
    private val mealDao = MealDatabase.getDatabase(application).mealDao()
    private val local = LocalDataResource(mealDao)

    // Repository
    private val repository = Repository(remote, local)

    private var _mealDetail: MutableLiveData<NetworkResult<MealDetail>> = MutableLiveData()
    val mealDetail: LiveData<NetworkResult<MealDetail>> = _mealDetail

    fun fetchMealDetail(mealId: String) {
        viewModelScope.launch {
            repository.remote!!.getMealDetail(mealId).collect { result ->
                _mealDetail.value = result
            }
        }
    }

    val favoriteMealList: LiveData<List<MealEntity>> = repository.local!!.listMeal().asLiveData()
    fun insertFavoriteMeal(mealEntity: MealEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local!!.insertMeal(mealEntity)
        }
    }

    fun deleteFavoriteMeal(mealEntity: MealEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local!!.deleteMeal(mealEntity)
        }
    }
}