package com.example.recipeapp.network

import com.example.recipeapp.network.dto.RecipeDetailsDTO
import com.example.recipeapp.network.dto.RecipesListDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeApi {
    @GET("recipes")
    suspend fun getRecipes(): RecipesListDTO

    @GET("recipes/{uuid}")
    suspend fun getRecipeDetails(@Path("uuid") uuid: String): RecipeDetailsDTO
}