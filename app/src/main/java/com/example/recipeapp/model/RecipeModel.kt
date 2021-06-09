package com.example.recipeapp.model

import com.example.recipeapp.network.RecipeApiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeModel(
    private val api: RecipeApiRepository
) {

    suspend fun getRecipes() = withContext(Dispatchers.IO) {
        api.getRecipes()
    }

    suspend fun getRecipe(recipeUuid: String) = withContext(Dispatchers.IO) {
        api.getRecipeDetails(recipeUuid)
    }
}