package com.bootcamp.andrifp_mealdb.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bootcamp.andrifp_mealdb.R
import com.bootcamp.andrifp_mealdb.data.database.MealEntity
import com.bootcamp.andrifp_mealdb.databinding.ActivityFavoriteDetailBinding
import com.bootcamp.andrifp_mealdb.viewmodel.FavDetailViewModel

class FavDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteDetailBinding
    private val favoriteDetailViewModel by viewModels<FavDetailViewModel>()

    companion object {
        const val EXTRA_FAVORITE_MEAL = "favorite_meal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.apply {
            title = "Meal Detail"
            setBackgroundDrawable(ColorDrawable(Color.parseColor("#212121")))
            setDisplayHomeAsUpEnabled(true)
        }

        val favoriteMeal = intent.getParcelableExtra<MealEntity>(EXTRA_FAVORITE_MEAL)
        binding.mealDetail = favoriteMeal!!.meal

        binding.apply {
            favBtn.apply {
                setImageResource(R.drawable.ic_favorite)
                setOnClickListener {
                    deleteFavoriteMeal(favoriteMeal)
                    val intent = Intent(this@FavDetailActivity, FavActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun openWeb(url: String?) {
        val intent = Intent()
        intent.apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(url)
        }
        startActivity(intent)
    }

    private fun deleteFavoriteMeal(mealEntity: MealEntity) {
        favoriteDetailViewModel.deleteFavoriteMeal(mealEntity)
        Toast.makeText(this, "Successfully remove from favorite(s)", Toast.LENGTH_SHORT).show()
    }
}