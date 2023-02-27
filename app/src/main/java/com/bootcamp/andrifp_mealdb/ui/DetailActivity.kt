package com.bootcamp.andrifp_mealdb.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bootcamp.andrifp_mealdb.R
import com.bootcamp.andrifp_mealdb.data.database.MealEntity
import com.bootcamp.andrifp_mealdb.data.network.handler.NetworkResult
import com.bootcamp.andrifp_mealdb.data.network.model.MealsItem
import com.bootcamp.andrifp_mealdb.data.network.model.MealsItems
import com.bootcamp.andrifp_mealdb.databinding.ActivityDetailBinding
import com.bootcamp.andrifp_mealdb.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var mealDetail: MealsItem
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    companion object {
        const val EXTRA_MEAL = "meal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            title = "Meal Detail"
            setBackgroundDrawable(ColorDrawable(Color.parseColor("#212121")))
            setDisplayHomeAsUpEnabled(true)
        }

        val meal = intent.getParcelableExtra<MealsItems>(EXTRA_MEAL)

        detailViewModel.fetchMealDetail(meal?.idMeal as String)

        detailViewModel.mealDetail.observe(this) { res ->
            when (res) {
                is NetworkResult.Loading -> {
                    handleUi(wrapper = false, progress = true, errorTv = false)
                }
                is NetworkResult.Error -> {
                    handleUi(wrapper = false, progress = false, errorTv = true)
                }
                is NetworkResult.Success -> {
                    val data = res.data?.meals?.get(0)
                    mealDetail = res.data?.meals?.get(0)!!
                    binding.apply {
                        mealDetail = data
                    }
                    handleUi(wrapper = true, progress = false, errorTv = false)
                }
            }
        }

        isFavoriteMeal(meal)
    }

    private fun handleUi(
        wrapper: Boolean,
        progress: Boolean,
        errorTv: Boolean
    ) {
        binding.apply {
            mealDetailWrapper.isVisible = wrapper
            progressBar.isVisible = progress
            errorText.isVisible = errorTv
        }
    }

    private fun isFavoriteMeal(mealSelected: MealsItems) {
        detailViewModel.favoriteMealList.observe(this) { result ->
            val meal = result.find { favorite ->
                favorite.meal.idMeal == mealSelected.idMeal
            }
            if (meal != null) {
                binding.favBtn.apply {
                    setImageResource(R.drawable.ic_favorite)
                    setOnClickListener {
                        deleteFavoriteMeal(meal.id)
                    }
                }
            } else {
                binding.favBtn.apply {
                    setImageResource(R.drawable.ic_add_favorite)
                    setOnClickListener {
                        insertFavoriteMeal()
                    }
                }
            }
        }
    }

    private fun insertFavoriteMeal() {
        val mealEntity = MealEntity(meal = mealDetail)
        detailViewModel.insertFavoriteMeal(mealEntity)
        Toast.makeText(this, "Successfully add to favorite(s)", Toast.LENGTH_SHORT).show()
    }

    private fun deleteFavoriteMeal(mealEntityId: Int) {
        val mealEntity = MealEntity(mealEntityId, mealDetail)
        detailViewModel.deleteFavoriteMeal(mealEntity)
        Toast.makeText(this, "Successfully remove from favorite(s)", Toast.LENGTH_SHORT).show()
    }

    private fun openWeb(url: String?) {
        val intent = Intent()
        intent.apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(url)
        }
        startActivity(intent)
    }
}