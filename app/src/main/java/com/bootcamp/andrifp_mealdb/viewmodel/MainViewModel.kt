package com.bootcamp.andrifp_mealdb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bootcamp.andrifp_mealdb.data.network.RemoteDataSource
import com.bootcamp.andrifp_mealdb.data.network.Repository
import com.bootcamp.andrifp_mealdb.data.network.handler.NetworkResult
import com.bootcamp.andrifp_mealdb.data.network.model.MealList
import com.bootcamp.andrifp_mealdb.data.network.Service
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val remoteService = Service.mealService
    private val remote = RemoteDataSource(remoteService)
    private val repository = Repository(remote)

    private var _mealsList: MutableLiveData<NetworkResult<MealList>> = MutableLiveData()
    val mealsList: LiveData<NetworkResult<MealList>> = _mealsList

    init {
        fetchMeals()
    }

    private fun fetchMeals() {
        viewModelScope.launch {
            repository.remote!!.getMeals().collect { res -> _mealsList.value = res }
        }
    }
}