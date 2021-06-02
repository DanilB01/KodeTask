package com.example.recipeapp.model

import android.content.Context
import com.example.app.network.RecipeApiRepository
import com.example.recipeapp.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainModel(private val app: Context) {

    private val api = RecipeApiRepository(Network.retrofit)

    suspend fun getRecipes() = withContext(Dispatchers.IO) {
            api.getRecipes()
    }

}