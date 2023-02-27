package com.bootcamp.andrifp_mealdb.binding_adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bootcamp.andrifp_mealdb.R
import com.bumptech.glide.Glide

object BindAdapter {
    @BindingAdapter("loadImageFromUrl")
    @JvmStatic
    fun loadImageFromUrl(imageView: ImageView, imageUrl: String?) {
        val placeHolderDrawable = R.drawable.ic_launcher_background
        Glide.with(imageView.context)
            .load(imageUrl).placeholder(placeHolderDrawable)
            .error(placeHolderDrawable)
            .into(imageView)
    }
}
