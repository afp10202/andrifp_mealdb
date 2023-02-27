package com.bootcamp.andrifp_mealdb.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bootcamp.andrifp_mealdb.adapter.MealAdapter
import com.bootcamp.andrifp_mealdb.data.network.handler.NetworkResult
import com.bootcamp.andrifp_mealdb.data.network.model.MealsItems
import com.bootcamp.andrifp_mealdb.databinding.ActivityMainBinding
import com.bootcamp.andrifp_mealdb.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.hide()

        binding.favoriteIcon.setOnClickListener {
            val intent = Intent(this@MainActivity, FavActivity::class.java)
            startActivity(intent)
        }

        mainViewModel.mealsList.observe(this@MainActivity) { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    handleUi(recyclerView = false, progress = true, errorTv = false)
                }
                is NetworkResult.Error -> {
                    binding.apply {
                        errorText.text = "Something went wrong, Please check log"
                    }
                    handleUi(recyclerView = false, progress = false, errorTv = true)
                }
                is NetworkResult.Success -> {
                    val mealsAdapter = MealAdapter()
                    mealsAdapter.setData(result.data?.meals as List<MealsItems>)
                    binding.rvMeal.apply {
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        setHasFixedSize(true)
                        adapter = mealsAdapter
                    }
                    mealsAdapter.setOnClickCallback(object : MealAdapter.IOnItemCallBack {
                        override fun onItemClickCallback(data: MealsItems) {
                            val intent = Intent(this@MainActivity, DetailActivity::class.java)
                            intent.putExtra(DetailActivity.EXTRA_MEAL, data)
                            startActivity(intent)
                        }
                    })
                    handleUi(recyclerView = true, progress = false, errorTv = false)
                }
            }
        }
    }

    private fun handleUi(
        recyclerView: Boolean,
        progress: Boolean,
        errorTv: Boolean
    ) {
        binding.apply {
            rvMeal.isVisible = recyclerView
            progressBar.isVisible = progress
            errorText.isVisible = errorTv
        }
    }
}