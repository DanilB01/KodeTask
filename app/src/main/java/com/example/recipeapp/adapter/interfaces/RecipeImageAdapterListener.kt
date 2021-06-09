package com.example.recipeapp.adapter.interfaces

interface RecipeImageAdapterListener: RecipeAdapterListener {
    fun openImage(imageURL: String)
}