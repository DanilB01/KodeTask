package com.example.recipeapp.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.recipeapp.R

fun loadImage(url: String?, view: ImageView) {
    Glide
            .with(view)
            .load(url)
            .into(view)
}

fun loadImageWithPlaceholder(url: String?, view: ImageView){
    Glide
            .with(view)
            .load(url)
            .placeholder(R.drawable.pic_error)
            .into(view)
}