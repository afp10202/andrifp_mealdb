package com.bootcamp.andrifp_mealdb.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bootcamp.andrifp_mealdb.adapter.FavAdapter
import com.bootcamp.andrifp_mealdb.data.database.MealEntity
import com.bootcamp.andrifp_mealdb.databinding.ActivityFavoriteBinding
import com.bootcamp.andrifp_mealdb.viewmodel.FavViewModel

class FavActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel by viewModels<FavViewModel>()
    private val favoriteAdapter by lazy { FavAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.apply {
            title = "Favorite"
            setBackgroundDrawable(ColorDrawable(Color.parseColor("#212121")))
            setDisplayHomeAsUpEnabled(true)
        }

        favoriteViewModel.favoriteMealList.observe(this) { result ->
            if (result.isEmpty()) {
                binding.apply {
                    rvFavoriteMeal.isVisible = false
                    emptyDesc.isVisible = true
                    emptyText.isVisible = true
                    deleteWrapper.isVisible = false
                }
            } else {
                binding.apply {
                    rvFavoriteMeal.apply {
                        adapter = favoriteAdapter
                        layoutManager = LinearLayoutManager(this@FavActivity)
                    }
                    deleteAll.setOnClickListener {
                        deleteAllFavorites()
                    }
                }
                favoriteAdapter.apply {
                    setData(result)
                    setOnItemClickCallback(object : FavAdapter.IOnFavoriteItemCallBack {
                        override fun onFavoriteItemClickCallback(data: MealEntity) {
                            val intent = Intent(this@FavActivity, FavDetailActivity::class.java)
                            intent.putExtra(FavDetailActivity.EXTRA_FAVORITE_MEAL, data)
                            startActivity(intent)
                        }
                    })
                }
            }
        }
    }

    private fun deleteAllFavorites() {
        favoriteViewModel.deleteAllMeals()
        Toast.makeText(this, "Successfully delete all favorite(s)", Toast.LENGTH_SHORT).show()
    }
}