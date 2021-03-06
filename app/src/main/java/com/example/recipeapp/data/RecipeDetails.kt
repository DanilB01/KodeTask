package com.example.recipeapp.data

data class RecipeDetails(
    val uuid: String,
    val name: String,
    val images: List<String>,
    val lastUpdated: Int,
    val description: String?,
    val instructions: String,
    val difficulty: Int,
    val similar: List<RecipeBrief>
)
