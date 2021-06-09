package com.example.recipeapp.model

import com.example.recipeapp.network.RecipeApiRepository
import com.example.recipeapp.data.Recipe
import com.example.recipeapp.data.RecipeDetails
import com.example.recipeapp.network.Network
import com.example.recipeapp.network.RecipeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject

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